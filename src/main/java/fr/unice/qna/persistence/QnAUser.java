package fr.unice.qna.persistence;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


import javax.persistence.*;

import java.io.Serializable;
import java.util.Collection;
import java.util.ArrayList;
import java.util.List;

@Entity
public class QnAUser implements Serializable, UserDetails {

	private static final long serialVersionUID = 1L;


	@Id
	private String username;
	private String email;
	private String password;

	private static BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

	public QnAUser() {

	}

	public QnAUser(String username, String email, String password) {
		this.username = username;
		this.email = email;
		this.password = encoder.encode(password);
		System.out.println(this.password);
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = encoder.encode(password);
	}

	public String toString() {
		return String.format("QnAUser[username: %s, password: %s, email: %s]",username,password,email);
	}

	public boolean isAccountNonExpired() {
		return true;
	}

	public boolean isAccountNonLocked() {
		return true;
	}

	public boolean isCredentialsNonExpired() {
		return true;
	}

	public boolean isEnabled() {
		return true;
	}

	public Collection<? extends GrantedAuthority> getAuthorities() {
		List<GrantedAuthority> res = new ArrayList<GrantedAuthority>();
		res.add(new SimpleGrantedAuthority("USER"));
		return res;
	}

	@Override
	public int hashCode() {
		return username.hashCode() + password.hashCode() 
			+ email.hashCode()
			+ getAuthorities().hashCode();
	}

	@Override
	public boolean equals(Object o) {
		if(o instanceof QnAUser) {
			QnAUser u = (QnAUser)o;
			return username.equals(u.username) && password.equals(u.password)
				&& email.equals(u.email) && getAuthorities().equals(u.getAuthorities());
		}
		return false;
	}

}
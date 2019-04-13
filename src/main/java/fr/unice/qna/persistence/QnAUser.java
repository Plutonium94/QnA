package fr.unice.qna.persistence;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Collection;
import java.util.ArrayList;

@Entity
public class QnAUser implements Serializable, UserDetails {

	private static final long serialVersionUID = 1L;


	@Id
	private String username;
	private String email;
	private String password;

	public QnAUser() {

	}

	public QnAUser(String username, String email, String password) {
		this.username = username;
		this.email = email;
		this.password = password;
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
		this.password = password;
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
		return new ArrayList<GrantedAuthority>();
	}

	@Override
	public int hashCode() {
		return username.hashCode() + password.hashCode() + email.hashCode();
	}

	@Override
	public boolean equals(Object o) {
		if(o instanceof QnAUser) {
			QnAUser u = (QnAUser)o;
			return username.equals(u.username) && password.equals(u.password)
				&& email.equals(u.email);
		}
		return false;
	}

}
package fr.unice.qna;

import fr.unice.qna.persistence.QnAUser;
import fr.unice.qna.persistence.QnAUserRepository;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.provisioning.InMemoryUserDetailsManagerConfigurer;
import org.springframework.security.config.annotation.authentication.configurers.provisioning.UserDetailsManagerConfigurer;
import org.springframework.security.web.util.matcher.RegexRequestMatcher;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	private BCryptPasswordEncoder encoder;
	private QnAUserRepository qur;

	public SecurityConfig(QnAUserRepository qur) {
		// encoder = new BCryptPasswordEncoder();		
		this.qur = qur;
	} 


	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.authorizeRequests()
			.mvcMatchers(HttpMethod.GET, "/questions/new").authenticated()
			.mvcMatchers(HttpMethod.GET, "/questions/*").permitAll()
			.mvcMatchers(HttpMethod.GET, "/").permitAll()
			.anyRequest().authenticated()
			.and()
			.formLogin()
			.and()
			.csrf()
			.disable();
	}

	/*@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {*/
		/*UserDetailsManagerConfigurer imudmc = auth.inMemoryAuthentication();
		imudmc = imudmc.passwordEncoder(encoder);
		UserDetailsManagerConfigurer.UserDetailsBuilder udb = imudmc.withUser("Joe");
		udb = udb.password(encoder.encode("apple"));
		udb = udb.roles("USER");
		imudmc = udb.and();
		udb = imudmc.withUser("Pierre");
		udb = udb.password(encoder.encode("orange"));
		udb = udb.roles("USER","ADMIN");
		*/
		/*auth.inMemoryAuthentication()
			.passwordEncoder(encoder)
			.withUser("Joe")
			.password(encoder.encode("apple"))
			.roles("USER")
			.and()
			.withUser("Pierre")
			.password(encoder.encode("orange"))
			.roles("USER","ADMIN");*/

	/*	UserDetails u1 = new QnAUser("Joe","apple","joe@joe.com");
		UserDetails u2 = new QnAUser("Pierre","orange","pierre@joe.com");

		
	}*/

	@Override
	protected void configure(AuthenticationManagerBuilder amb) throws Exception {
		amb.userDetailsService(this.qur)		
			.passwordEncoder(getEncoder());
	}

	@Bean
	public BCryptPasswordEncoder getEncoder() {
		return new BCryptPasswordEncoder();
	}

	/*@Bean
	public QnAUserRepository getQnAUserRepository() {
		return new QnAUserRepository();
	}*/


}
package fr.unice.qna;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.provisioning.InMemoryUserDetailsManagerConfigurer;
import org.springframework.security.config.annotation.authentication.configurers.provisioning.UserDetailsManagerConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	private BCryptPasswordEncoder encoder;

	public SecurityConfig() {
		encoder = new BCryptPasswordEncoder();		
	} 


	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
			.anyRequest().authenticated()
			.and()
			.formLogin();
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
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
		auth.inMemoryAuthentication()
			.passwordEncoder(encoder)
			.withUser("Joe")
			.password(encoder.encode("apple"))
			.roles("USER")
			.and()
			.withUser("Pierre")
			.password(encoder.encode("orange"))
			.roles("USER","ADMIN");
	}


}
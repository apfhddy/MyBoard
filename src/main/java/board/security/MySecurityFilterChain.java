package board.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.RegexRequestMatcher;

@Configuration
@EnableWebSecurity
public class MySecurityFilterChain { 
	 
	@Bean
	public SecurityFilterChain filter(HttpSecurity http) throws Exception {
		http
			.authorizeHttpRequests( auth -> 
				auth
					.requestMatchers(
							"/","/login","/join","/rs/**","/search","/imageToString",
							"/certified/*","/user/check/*","/test"
					).permitAll()
					.requestMatchers(RegexRequestMatcher.regexMatcher("/[0-9]+")).permitAll()
					.requestMatchers("/admin/**").hasRole("admin")
					.requestMatchers("/write").hasRole("board_write")
					.requestMatchers("/comment/write").hasRole("comment_write")
					.anyRequest().authenticated()
			)
			.formLogin(form -> 
				form
					.loginPage("/login")
					.loginProcessingUrl("/login")
					.usernameParameter("id")
					.passwordParameter("pw")
					.defaultSuccessUrl("/",true)
					.permitAll()
			)
			.logout(logout -> 
				logout
					.logoutUrl("/logout")
					.logoutSuccessUrl("/")
			)
		;
		return http.build();
	}
	
	
	@Bean
	public PasswordEncoder encoding() {
		return new BCryptPasswordEncoder();
	}
}

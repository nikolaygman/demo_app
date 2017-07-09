package com.web.app.configuration.spring.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.web.app.service.UserService;

@Configuration
@EnableWebSecurity
@ComponentScan("com.web.app.service")
public class SpringSecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired
	UserService userService;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userService);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.authorizeRequests().antMatchers("/logout").hasAuthority("ROLE_USER")
				.and()
			.authorizeRequests().antMatchers("/login","/register").anonymous()
				.and()
			.formLogin().loginPage("/login").usernameParameter("username").defaultSuccessUrl("/")
				.and()
			.logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout")).logoutSuccessUrl("/")
				.and()
			.rememberMe()
				.and().exceptionHandling().accessDeniedPage("/redirect_home");


	}

}

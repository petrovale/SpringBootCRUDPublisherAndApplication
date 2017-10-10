package com.isakov.springboot.configuration;

import com.isakov.springboot.service.PublisherServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import static com.isakov.springboot.util.PasswordUtil.PASSWORD_ENCODER;


@Configuration
@EnableWebSecurity
@ComponentScan("com.isakov.springboot")
public class MultiWebSecurityConfig {
    @Autowired
    private PublisherServiceImpl publisherService;

	@Configuration
	@Order(1)
	public static class ApiWebSecurityConfig extends WebSecurityConfigurerAdapter{
		@Override
		protected void configure(HttpSecurity http) throws Exception {
			http.csrf().disable()
					.authorizeRequests()
					.antMatchers("/rest/admin/**").hasRole("ADMIN")
					.anyRequest().authenticated()
					.and()
					.antMatcher("/rest/**")
					.authorizeRequests()
					.anyRequest().authenticated()
					.and()
					.httpBasic();
		}
	}

	@Configuration
	public static class FormLoginWebSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {

		@Override
		protected void configure(HttpSecurity http) throws Exception {
			http
					.authorizeRequests().antMatchers("/css/**", "/signup", "/saveuser", "/api/**").permitAll() // Enable css when logged out
					.and()
					.authorizeRequests()
					.anyRequest().authenticated()
					.and()
					.formLogin()
					.loginPage("/login")
					.defaultSuccessUrl("/apps")
					.permitAll()
					.and()
					.logout()
					.permitAll()
					.and();
		}
	}
     
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
    	auth.userDetailsService(publisherService).passwordEncoder(PASSWORD_ENCODER);
    }
}
package com.isakov.springboot.configuration;

import com.isakov.springboot.service.PublisherServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


@Configuration
@EnableWebSecurity
@ComponentScan("com.isakov.springboot")
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private PublisherServiceImpl publisherService;
    
	@Override
    protected void configure(HttpSecurity http) throws Exception {
    	http
    		.authorizeRequests().antMatchers("/css/**", "/signup", "/saveuser").permitAll() // Enable css when logged out
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
     
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
    	auth.userDetailsService(publisherService).passwordEncoder(new BCryptPasswordEncoder());
    }
}
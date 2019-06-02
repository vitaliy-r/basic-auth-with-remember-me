package com.epam.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  @Value("${app.security.remember-me.cookie-name}")
  private String rememberMeCookieName;

  @Value("${app.security.remember-me.secret-key}")
  private String secreatKey;

  @Value("${app.security.remember-me.token-lifetime}")
  private Integer tokenLifetime;

  @Override
  public void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.inMemoryAuthentication()
        .withUser("user").password("{noop}password").authorities("ROLE_USER").and()
        .withUser("admin").password("{noop}password").authorities("ROLE_USER", "ROLE_ADMIN");
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http
        .authorizeRequests()
          .antMatchers("/", "/home").permitAll()
          .antMatchers("/hello").hasAnyRole("USER", "ADMIN")
          .antMatchers("/admin").hasRole("ADMIN")
          .anyRequest().authenticated()
          .and()
        .formLogin()
          .loginPage("/login")
            .permitAll()
          .and()
        .rememberMe()
          .key(secreatKey)
          .tokenValiditySeconds(tokenLifetime)
          .rememberMeCookieName(rememberMeCookieName)
          .rememberMeParameter("remember-me")
          .and()
        .logout()
          .deleteCookies("JSESSIONID")
        .and()
          .csrf().disable();
  }

}
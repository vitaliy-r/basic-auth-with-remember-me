package com.epam.config;

import com.epam.session.SessionStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.session.HttpSessionEventPublisher;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  private static final String SESSION_PARAMETER_NAME = "JSESSIONID";
  private static final String REMEMBER_ME_PARAMETER_NAME = "remember-me";

  @Value("${app.security.remember-me.cookie-name}")
  private String rememberMeCookieName;

  @Value("${app.security.remember-me.secret-key}")
  private String secreatKey;

  @Value("${app.security.remember-me.token-lifetime}")
  private Integer tokenLifetime;

  @Autowired
  private AuthenticationSuccessHandler loginSuccessHandler;
  @Autowired
  private LogoutSuccessHandler logoutSuccessHandler;

  @Override
  public void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.inMemoryAuthentication()
        .withUser("user").password("{noop}password").authorities("ROLE_USER").and()
        .withUser("admin").password("{noop}password").authorities("ROLE_USER", "ROLE_ADMIN");
  }

  @Override
  protected void configure(final HttpSecurity http) throws Exception {
    http
        .authorizeRequests()
          .antMatchers("/", "/home").permitAll()
          .antMatchers("/hello").hasAnyRole("USER", "ADMIN")
          .antMatchers("/admin").hasRole("ADMIN")
          .anyRequest().authenticated()
          .and()
        .formLogin()
          .loginPage("/login").permitAll()
          .successHandler(loginSuccessHandler)
          .and()
        .rememberMe()
          .key(secreatKey)
          .tokenValiditySeconds(tokenLifetime)
          //.useSecureCookie(true)
          .rememberMeCookieName(rememberMeCookieName)
          .rememberMeParameter(REMEMBER_ME_PARAMETER_NAME)
          .and()
        .logout()
          .invalidateHttpSession(true)
          .logoutSuccessHandler(logoutSuccessHandler)
          .deleteCookies(SESSION_PARAMETER_NAME)
          .and()
        .csrf()
          .disable()
        .sessionManagement()
          .maximumSessions(1)
          .sessionRegistry(sessionRegistry());
  }

  @Bean
  public SessionRegistry sessionRegistry() {
    return new SessionRegistryImpl();
  }

  @Bean
  public HttpSessionEventPublisher httpSessionEventPublisher() {
    return new HttpSessionEventPublisher();
  }

  @Bean
  public SessionStore sessionStore(){
    return new SessionStore();
  }

}
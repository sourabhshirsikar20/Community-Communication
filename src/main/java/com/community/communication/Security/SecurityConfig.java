package com.community.communication.Security;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.jdbc.JdbcDaoImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import jakarta.annotation.PostConstruct;


@Configuration
@EnableWebSecurity
public class SecurityConfig {

  @Autowired
    private DataSource dataSource;
  
  private static final String[] ENDPOINTS_WHITELIST = {
    "/css/**",
    "/js/**",
    "/svg/**",
    "/SubmitBlock",
  };

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http.authorizeHttpRequests(request -> request
      .requestMatchers(ENDPOINTS_WHITELIST).hasRole("USER")
      .anyRequest().hasRole("MANAGER"))
      .csrf(csrf -> csrf.disable())
      .formLogin();

    return http.build();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
      return new BCryptPasswordEncoder();
  }

  @Bean
  UserDetailsService userDetailsService() {
    JdbcDaoImpl jdbcUserDetails = new JdbcDaoImpl();
    jdbcUserDetails.setDataSource(dataSource);
    jdbcUserDetails.setUsersByUsernameQuery("select login_name as username, password, enabled from logins where login_name = ?");
    jdbcUserDetails.setAuthoritiesByUsernameQuery("select login_name as username, authority from login_authorities where login_name = ?");
    return jdbcUserDetails;
  }
}

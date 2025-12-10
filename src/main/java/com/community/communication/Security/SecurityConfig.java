package com.community.communication.Security;

import java.util.regex.Pattern;

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

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletResponse;


@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private DataSource dataSource;

    private static final String[] ENDPOINTS_WHITELIST = {
        "/css/**", // css is needed for the login page, so must be allowed
        "/error"   // similarly, error page should be allowed in order to show "forbidden" messages
    };

    private static final String[] USER_WHITELIST = {
        "/js/**",
        "/svg/**",
        "/street",
        "/street/**",
        "/street-info/**",
        "/block-name/**",
        "/SubmitBlock",
        "/Index",
        "/OrganisationInfo",
        "/OrganisationInfo/**",
        "/BlockAccess/**",
        "/search",
        "/live-search"
    };

    private static final String[] MANAGER_WHITELIST = {
            "/Approver",
            "/approveBlock/**",
            "/Promos/**",
            "/test/expiry-status",
            "/api/expiry-status",
            "/api/expired-count",
    };

    public static boolean IsUrlInWhitelists(String url) {
        for (String pattern : ENDPOINTS_WHITELIST) {
            if (matchPattern(url, pattern))
                return true;
        }
        for (String pattern : USER_WHITELIST) {
            if (matchPattern(url, pattern))
                return true;
        }
        for (String pattern : MANAGER_WHITELIST) {
            if (matchPattern(url, pattern))
                return true;
        }
        return false;
    }

    public static  boolean IsUrlManager(String url){
        for (String pattern : MANAGER_WHITELIST) {
            if (matchPattern(url, pattern))
                return true;
        }
        return false;
    }

    // Converts url patterns to regex and checks if matches
    private static boolean matchPattern(String url, String pattern) {
        String regex = Pattern.quote(pattern).replace("\\*\\*", ".*").replace("\\*", "[^/]*");
        return url.matches(regex);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(request -> request
                        .requestMatchers(ENDPOINTS_WHITELIST).permitAll()
                        .requestMatchers(USER_WHITELIST).hasRole("USER")
                        .requestMatchers(MANAGER_WHITELIST).hasRole("MANAGER")
                        .anyRequest().hasRole("ADMIN"))
                .csrf(csrf -> csrf.disable())
                .formLogin(form -> form.loginPage("/Login"). permitAll());

        http.exceptionHandling(ex -> ex
            .accessDeniedHandler((request, response, ex2) -> {
                request.setAttribute(RequestDispatcher.ERROR_STATUS_CODE, HttpServletResponse.SC_FORBIDDEN);
                request.setAttribute(RequestDispatcher.ERROR_MESSAGE, ex2.getMessage());
                request.getRequestDispatcher("/error").forward(request, response);
            })
        );

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

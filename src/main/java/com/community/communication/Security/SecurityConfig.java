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
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.Cookie;
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
            "/ManageStreets",
            "/new-street",
            "/move-street",
            "street-blocks/**",
            "/remove-street/**",
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

    public static boolean IsUrlAdmin(String url) {
        // Returns true if URL requires ADMIN access ONLY
        // (i.e., NOT accessible to USER or MANAGER roles)

        // Check if it's a public resource
        for (String pattern : ENDPOINTS_WHITELIST) {
            if (matchPattern(url, pattern)) {
                return false;
            }
        }

        // Check if user can access
        for (String pattern : USER_WHITELIST) {
            if (matchPattern(url, pattern)) {
                return false;
            }
        }

        // Check if manager can access
        for (String pattern : MANAGER_WHITELIST) {
            if (matchPattern(url, pattern)) {
                return false;
            }
        }

        // If not in any whitelist, admin-only access required
        return true;
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
                .formLogin(form -> form.loginPage("/Login")

                .successHandler((request, response, authentication) -> {

                    // Check if user voluntarily logged out (via cookie)
                    boolean userInitiatedLogout = false;
                    Cookie[] cookies = request.getCookies();
                    if (cookies != null) {
                        for (Cookie cookie : cookies) {
                            if ("user_logout".equals(cookie.getName())) {
                                userInitiatedLogout = true;
                                // Delete the cookie after reading it
                                cookie.setMaxAge(0);
                                cookie.setPath("/");
                                response.addCookie(cookie);
                                break;
                            }
                        }
                    }

                    if (userInitiatedLogout) {
                        // User clicked logout button → always go to Index
                        response.sendRedirect("/Index");
                    } else {
                        // User was blocked from a page → go to that page
                        HttpSessionRequestCache requestCache = new HttpSessionRequestCache();
                        SavedRequest savedRequest = requestCache.getRequest(request, response);

                        if (savedRequest != null) {
                            String targetUrl = savedRequest.getRedirectUrl();
                            requestCache.removeRequest(request, response);
                            response.sendRedirect(targetUrl);
                        } else {
                            // No saved request, go to Index
                            response.sendRedirect("/Index");
                        }
                    }
                })
                .permitAll())
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .addLogoutHandler((request, response, authentication) -> {
                            // Set cookie to indicate user clicked logout
                            Cookie logoutCookie = new Cookie("user_logout", "true");
                            logoutCookie.setMaxAge(0); 
                            logoutCookie.setPath("/");
                            response.addCookie(logoutCookie);
                            
                            // Clear saved request
                            HttpSessionRequestCache requestCache = new HttpSessionRequestCache();
                            requestCache.removeRequest(request, response);
                        })
                        .logoutSuccessUrl("/Login?logout")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                        .clearAuthentication(true)
                        .permitAll())
                
                .headers(headers -> headers
                        .cacheControl(cache -> cache.disable()));

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

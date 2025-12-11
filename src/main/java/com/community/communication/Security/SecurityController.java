package com.community.communication.Security;

import java.net.URI;
import java.net.URISyntaxException;

import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Controller
public class SecurityController {

  @GetMapping("/Login")
  public ModelAndView LoginPage(HttpServletRequest request, HttpServletResponse response){

    ModelAndView mav = new ModelAndView("Login");

    boolean managerUrlRequired = false;
    boolean adminUrlRequired = false;

    HttpSessionRequestCache requestCache = new HttpSessionRequestCache();
    SavedRequest savedRequest = requestCache.getRequest(request, response);

    // Check if user just logged out
      boolean userJustLoggedOut = false;

      // Check URL parameter (?logout)
      if (request.getParameter("logout") != null) {
          userJustLoggedOut = true;
      }

      // Check cookie
      Cookie[] cookies = request.getCookies();
      if (cookies != null) {
          for (Cookie cookie : cookies) {
              if ("user_logout".equals(cookie.getName())) {
                  userJustLoggedOut = true;
                  break;
              }
          }
      }

      // If user just logged out, clear saved request and ignore it
      if (userJustLoggedOut) {
          if (savedRequest != null) {
              requestCache.removeRequest(request, response);
              savedRequest = null;
          }
      }

      // Check URL requirements ONLY if saved request exists
      if (savedRequest != null) {
          try {
              URI redirectUrl = new URI(savedRequest.getRedirectUrl());
              String path = redirectUrl.getPath();

              // Check admin FIRST (higher priority)
              adminUrlRequired = SecurityConfig.IsUrlAdmin(path);

              // Only check manager if NOT admin
              // This prevents overlap between admin and manager URLs
              if (!adminUrlRequired) {
                  managerUrlRequired = SecurityConfig.IsUrlManager(path);
              }

          } catch (URISyntaxException e) {
              System.err.println("Error parsing saved request URL: " + e.getMessage());
          }
      }

      // ALWAYS ADD THESE - EVEN WHEN savedRequest IS NULL
      // This ensures Login.html always has these variables
      mav.addObject("manager_url", managerUrlRequired);
      mav.addObject("admin_url", adminUrlRequired);

    return mav;
  }
}

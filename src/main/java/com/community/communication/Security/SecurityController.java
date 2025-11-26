package com.community.communication.Security;

import java.net.URI;
import java.net.URISyntaxException;

import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class SecurityController {

  @GetMapping("/Login")
  public ModelAndView LoginPage(HttpServletRequest request){

    ModelAndView mav = new ModelAndView("Login");

    HttpSessionRequestCache request_cache = new HttpSessionRequestCache();
    SavedRequest saved_request = request_cache.getRequest(request, null);

    URI redirect_url = null;
    if (saved_request != null) {
      try {
        redirect_url = new URI(saved_request.getRedirectUrl()); // gets the originally requested URL
        mav.addObject("manager_url", !SecurityConfig.IsUrlInWhitelists(redirect_url.getPath()));
      } catch (URISyntaxException e) {
        mav.addObject("manager_url", false);
      }
    }

    return mav;
  }
}

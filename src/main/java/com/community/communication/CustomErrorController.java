package com.community.communication;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;

@Controller
public class CustomErrorController implements  ErrorController {

  @RequestMapping("/error")
  public ModelAndView handleError(HttpServletRequest request) {
    Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
    ModelAndView page = new ModelAndView("error");

    page.addObject("hasError", status != null);
      
    if (status != null) {
      page.addObject("statusCode", status.toString());
    }

    return page;
  }

}

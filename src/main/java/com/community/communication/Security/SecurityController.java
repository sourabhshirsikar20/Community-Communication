package com.community.communication.Security;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class SecurityController {

  @GetMapping("/Login")
  public ModelAndView LoginPage(Model model){
    ModelAndView mav = new ModelAndView("Login");
    return mav;
  }

}

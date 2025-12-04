package com.community.communication.Manager;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class ManageStreetsController {
  
  @GetMapping("/ManageStreets")
  public ModelAndView ManageStreetsPage() {
      return new ModelAndView("ManageStreets");
  }
  
}

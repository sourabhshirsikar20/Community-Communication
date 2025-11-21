package com.community.communication.SubmitBlock;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import com.community.communication.Approved.ApproverRepository;

@Controller
public class SubmitController {

  @Autowired
  private ApproverRepository approverRepository;

  @GetMapping("/SubmitBlock")
  public ModelAndView SubmitBlockPage(){
      ModelAndView mav = new ModelAndView("SubmitBlock.html");
      return mav;
  }
}

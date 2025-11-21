package com.community.communication.SubmitBlock;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import com.community.communication.Approved.ApproverRepository;
import com.community.communication.Approved.Block;

@Controller
public class SubmitController {

  @Autowired
  private ApproverRepository approverRepository;

  @GetMapping("/SubmitBlock")
  public ModelAndView SubmitBlockPage(){
    Block empty_block = new Block();
    ModelAndView mav = new ModelAndView("SubmitBlock.html");
    mav.addObject("block", empty_block);
    return mav;
  }
}

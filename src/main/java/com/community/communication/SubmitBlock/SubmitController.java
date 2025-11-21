package com.community.communication.SubmitBlock;

import org.jsoup.Jsoup;
import org.jsoup.safety.Safelist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.community.communication.Approved.ApproverRepository;
import com.community.communication.Approved.Block;

import jakarta.validation.Valid;

@Controller
public class SubmitController {

  @Autowired
  private ApproverRepository approverRepository;

  @GetMapping("/SubmitBlock")
  public ModelAndView SubmitBlockPage(){
    Block empty_block = new Block();
    ModelAndView mav = new ModelAndView("SubmitBlock");
    mav.addObject("block", empty_block);
    return mav;
  }

  private String SanitiseString(String input) {
    return Jsoup.clean(input, Safelist.basic());
  }

  @PostMapping("/SubmitBlock")
  public ModelAndView submitForm(@Valid @ModelAttribute("block") Block block, BindingResult bindingResult, Model model){
    if (bindingResult.hasErrors()) {
      ModelAndView modelAndView = new ModelAndView("SubmitBlock", model.asMap());
      return modelAndView;
    }

    // sanitise input
    block.setName(SanitiseString(block.getName()));
    block.setMainText(SanitiseString(block.getMainText()));
    block.setLinksText(SanitiseString(block.getLinksText()));
    block.setInfoText(SanitiseString(block.getInfoText()));
    block.setPromoText(SanitiseString(block.getPromoText()));

    // add to database
    approverRepository.addNewBlock(block);

    ModelAndView modelAndView = new ModelAndView("redirect:/SubmitBlockSuccess");
    return modelAndView;
  }
}

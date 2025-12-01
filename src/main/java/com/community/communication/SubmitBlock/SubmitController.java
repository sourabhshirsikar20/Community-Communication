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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.community.communication.Approved.ApproverRepository;
import com.community.communication.Approved.Block;

import jakarta.validation.Valid;

@Controller
public class SubmitController {

  @Autowired
  private ApproverRepository approverRepository;

  @GetMapping("/SubmitBlock")
  public ModelAndView SubmitBlockPage(Model model){
    ModelAndView mav = new ModelAndView("SubmitBlock");
    if (model.containsAttribute("successMessage")) {
        mav.addObject("successMessage", model.getAttribute("successMessage"));
    }
    if (!model.containsAttribute("block")) {
        mav.addObject("block", new SubmitBlock());
    }
    return mav;
  }

  private String SanitiseString(String input) {
    return Jsoup.clean(input, Safelist.basic());
  }

  @PostMapping("/SubmitBlock")
    public ModelAndView submitForm(@Valid @ModelAttribute("block") SubmitBlock block,
        BindingResult bindingResult,
        RedirectAttributes redirectAttributes) {

      if (bindingResult.hasErrors()) {
        ModelAndView mav = new ModelAndView("SubmitBlock");
        mav.addObject("block", block);
        mav.addObject(BindingResult.MODEL_KEY_PREFIX + "block", bindingResult); // required for Thymeleaf
        return mav;
      }

      // Sanitise input
      block.setName(SanitiseString(block.getName()));
      block.setMainText(SanitiseString(block.getMainText()));
      block.setLinksText(SanitiseString(block.getLinksText()));
      block.setInfoText(SanitiseString(block.getInfoText()));
      block.setPromoText(SanitiseString(block.getPromoText()));

      // Save block
      approverRepository.addNewBlock(new Block(block));

      redirectAttributes.addFlashAttribute("successMessage", "Block submitted successfully");
      return new ModelAndView("redirect:/SubmitBlock");
    }
}

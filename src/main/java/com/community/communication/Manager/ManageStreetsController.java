package com.community.communication.Manager;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.community.communication.Approved.ApproverRepository;
import com.community.communication.Approved.Block;
import com.community.communication.Approved.Street;

import jakarta.validation.Valid;


@Controller
public class ManageStreetsController {

  @Autowired
  private ApproverRepository ApproverRepository;
  
  @GetMapping("/ManageStreets")
  public ModelAndView ManageStreetsPage() {
    return new ModelAndView("ManageStreets");
  }

  @ResponseBody
  @GetMapping(path="/street-info", produces=MediaType.APPLICATION_JSON_VALUE)
  public List<Street> getStreetInfo() {
    return ApproverRepository.getAllStreets();
  }

  @ResponseBody
  @GetMapping(path="/street-blocks/{street}", produces=MediaType.APPLICATION_JSON_VALUE)
  public List<Block> getStreetBlocks(@PathVariable String street) {
    return ApproverRepository.getStreetBlocks(street.equals("root") ? "" : street);
  }

  @PostMapping("/new-street")
  public ModelAndView AddStreet(@Valid @ModelAttribute("newStreet") NewStreet block) {

    System.out.println(block);
      
    return new ModelAndView("redirect:/ManageStreets");
  }
  
  
}

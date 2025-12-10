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
    ModelAndView mav = new ModelAndView("ManageStreets");
    mav.addObject("newStreet", new NewStreet(null, null, null,null));
    mav.addObject("moveStreet", new MoveStreet(null, null));

    List<String> img_options = List.of("legacy", "return");
    mav.addObject("img_options", img_options);

    return mav;
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
  public ModelAndView AddStreet(@Valid @ModelAttribute("newStreet") NewStreet street) {

    Street db_street = new Street(-1, street.getName(), street.getAddress(), street.getDescription(), street.getImg_option());
    ApproverRepository.addNewStreet(db_street);
      
    return new ModelAndView("redirect:/ManageStreets");
  }

  void RemoveStreet(String street) {
      List<Street> substreets = ApproverRepository.getSubStreets(street);
      List<Block>  blocks     = ApproverRepository.getStreetBlocks(street);

      ApproverRepository.removeStreet(street);

      for (Block block : blocks) {
        ApproverRepository.removeBlock(block.getAddress());
      }

      for (Street substreet : substreets) {
        RemoveStreet(substreet.getAddress());
      }
  }

  @ResponseBody
  @PostMapping("/remove-street/{street}")
  public boolean RemoveStreetPage(@PathVariable String street) {
    try {
      RemoveStreet(street);
    } catch(Exception e){
      return false;
    }
    return true;
  }

  void MoveStreet(MoveStreet move) {
    // get street info
    List<Street> substreets = ApproverRepository.getSubStreets(move.getCurrentAddress());
    List<Block>  blocks     = ApproverRepository.getStreetBlocks(move.getCurrentAddress());
    Street       street     = ApproverRepository.getStreet(move.getCurrentAddress());

    street.setAddress(move.getNewAddress());

    // move
    ApproverRepository.removeStreet(move.getCurrentAddress());
    ApproverRepository.addNewStreet(street);

    for (Block block : blocks) {
      ApproverRepository.moveBlock(block.getAddress(), move.getNewAddress() + block.getAddress().substring(block.getAddress().length() - 2));
    }

    for (Street substreet : substreets) {
      MoveStreet(new MoveStreet(substreet.getAddress(), move.getNewAddress() + substreet.getAddress().substring(substreet.getAddress().length() - 2)));
    }
  }
  
  @PostMapping("/move-street")
  public ModelAndView MoveStreetPage(@Valid @ModelAttribute("moveStreet") MoveStreet move) {
    MoveStreet(move);
    return new ModelAndView("redirect:/ManageStreets");
  }
}

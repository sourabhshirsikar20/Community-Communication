package com.community.communication.OrganiseBlock;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/Block")
public class BlockController {
    @Autowired
    private BlockRepository blockRepository;

    @GetMapping("/View")
    public ModelAndView ViewBlock(@RequestParam(value = "Address", required = false) String Address){
        ModelAndView mav = new ModelAndView("BlockView");
        if(Address != null && !Address.isEmpty() && blockRepository.Check(Address)){
            Blocks block = blockRepository.ByAddress(Address);
            mav.addObject("Block", block);
        }
        mav.addObject("Address", Address);
        return mav;
    }
    @GetMapping("/Check")
    public ModelAndView CheckB(String Address){
        ModelAndView mav = new ModelAndView("BlockCheck");
        if(Address!=null && !Address.isEmpty()){
            boolean Exists = blockRepository.Check(Address);
            mav.addObject("Address", Address);
            mav.addObject("Exists", Exists);
        }
        return mav;
    }
}

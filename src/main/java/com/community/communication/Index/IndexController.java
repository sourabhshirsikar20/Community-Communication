package com.community.communication.Index;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import com.community.communication.Approved.ApproverRepository;
import com.community.communication.Approved.Block;

@Controller
public class IndexController {

    @Autowired
    private ApproverRepository ApproverRepository;

    
    @GetMapping("/Index")
    public ModelAndView ApprovedList(){
        ModelAndView mav = new ModelAndView("StreetView.html");
        List<Block> Approved = ApproverRepository.getApprovedBlocks();
        System.out.println(Approved);
        mav.addObject("Approved", Approved);
        return mav;
    }

    
}

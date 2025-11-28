package com.community.communication.Approved;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ApprovedController {

    @Autowired
    private ApproverRepository ApproverRepository;

    @GetMapping("/Approver")
    public ModelAndView unApprovedList(){
        ModelAndView mav = new ModelAndView("Approved.html");
        List<Block> entries = ApproverRepository.getUnapprovedBlocks();
        System.out.println(entries);
        mav.addObject("entries", entries);
        return mav;
    }

    @PostMapping("/approveBlock")
    public String approveBlock(@RequestParam("id") int id) { 
    ApproverRepository.approve(id); 
    
    System.out.println("Approved Block ID: " + id);

    return "redirect:/Approver"; 
    }
}

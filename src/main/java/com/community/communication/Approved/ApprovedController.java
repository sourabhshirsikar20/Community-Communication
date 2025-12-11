package com.community.communication.Approved;

import java.security.SecureRandom;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ApprovedController {

    @Autowired
    private ApproverRepository ApproverRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    private final SecureRandom random = new SecureRandom();

    @GetMapping("/Approver")
    public ModelAndView unApprovedList(){
        ModelAndView mav = new ModelAndView("Approved.html");
        List<Block> entries = ApproverRepository.getUnapprovedBlocks();
        System.out.println(entries);
        mav.addObject("entries", entries);
        return mav;
    }

    @PostMapping("/approveBlock")
    public ModelAndView approveBlock(@RequestParam("id") int id) {

        String rawCode = generateCode();
        String secretHash = passwordEncoder.encode(rawCode);

        ApproverRepository.approve(id, secretHash);

        System.out.println("Approved Block ID: " + id);
        System.out.println("Generated access code: " + rawCode);

        ModelAndView mav = new ModelAndView("ApprovedCode.html");
        mav.addObject("id", id);
        mav.addObject("code", rawCode);

        return mav;
    }
    
    @PostMapping("/deleteBlock")
    public String deleteBlock(@RequestParam("id") int id) {
        ApproverRepository.deleteBlock(id);
        return "redirect:/Approver";
    }

    private String generateCode() {
        int number = 100000 + random.nextInt(900000); // 6 digits
        return String.valueOf(number);
    }
    @GetMapping("/UpdateBlock")
    public ModelAndView updateBlock(@RequestParam("Address") String Address) {
        ModelAndView mav = new ModelAndView("UpdateAddress.html");
        List<Street> Streets = ApproverRepository.getAllStreets();
        mav.addObject("Streets", Streets);
        mav.addObject("Address", Address);
        return mav;
    }
    @PostMapping("/UpdateBlock/Move")
    public ModelAndView moveBlock(@RequestParam("OldAddress") String OldAddress, @RequestParam("Street") String Street){
        ModelAndView mav = new ModelAndView("UpdateAddress");
        List<Street> Streets = ApproverRepository.getAllStreets();
        mav.addObject("Streets", Streets);
        mav.addObject("OldAddress", OldAddress);
        String NewAddress = ApproverRepository.GenAddr(Street);
        if(NewAddress == null || NewAddress.isEmpty()){
            mav.addObject("Error","No Available Address on that Street");
            return mav;
        }
       ApproverRepository.updateAddress(OldAddress,NewAddress);
        mav.addObject("NewAddress", NewAddress);
        return mav;
    }
}
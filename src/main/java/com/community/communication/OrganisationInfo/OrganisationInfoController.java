package com.community.communication.OrganisationInfo;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import com.community.communication.Approved.Block;
import com.community.communication.Expiry.ExpiryService;

@Controller
public class OrganisationInfoController {
    
    @Autowired
    private OrganisationInfoRepository organisationInfoRepository;
    
    @Autowired
    private ExpiryService expiryService;  
    
    @GetMapping("/OrganisationInfo/{address}")
    public ModelAndView OrganisationInfoView(@PathVariable String address){
        ModelAndView mav = new ModelAndView("OrganisationInfo.html");
        
        Block Info = organisationInfoRepository.getInfo(address);
        
        //Get expiry information using service
        Map<String, Object> expiryInfo = expiryService.getExpiryInfo(Info);
        
        // Add block info to view
        mav.addObject("Info", Info);
        
        //Add all expiry info to view
        mav.addObject("expiryDate", expiryInfo.get("expiryDate"));
        mav.addObject("isExpired", expiryInfo.get("isExpired"));
        mav.addObject("daysRemaining", expiryInfo.get("daysRemaining"));
        mav.addObject("hoursRemaining", expiryInfo.get("hoursRemaining"));
        mav.addObject("minutesRemaining", expiryInfo.get("minutesRemaining"));
        mav.addObject("formattedExpiryDate", expiryInfo.get("formattedExpiryDate"));
        
        return mav;
    }
    
    @GetMapping("/Test")
    public ModelAndView Links(){
        ModelAndView mavv = new ModelAndView("Links.html");
        return mavv;
    }
}
package com.community.communication.OrganisationInfo;

import java.util.Map;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
        
        try {
            Block Info = organisationInfoRepository.getInfo(address);
            
            // Check if block is approved
            if (!Info.isApproved()) {
                mav.setViewName("error");
                mav.addObject("errorMessage", "This block is not yet approved and cannot be viewed.");
                return mav;
            }
            
            // Check if created_date exists before calculating expiry
            if (Info.getCreatedDate() == null) {
                mav.setViewName("error");
                mav.addObject("errorMessage", "This block has no creation date. Please contact support.");
                return mav;
            }
            
            //Get expiry information using service
            Map<String, Object> expiryInfo = expiryService.getExpiryInfo(Info);
            
            // Add block info to view
            mav.addObject("Info", Info);

            // Convert DateTime to ISO string for JavaScript compatibility
            DateTime expiryDate = (DateTime) expiryInfo.get("expiryDate");
            String expiryDateISO = expiryDate.toString("yyyy-MM-dd'T'HH:mm:ss");
            
            //Add all expiry info to view
            mav.addObject("expiryDate",expiryDateISO);
            mav.addObject("isExpired", expiryInfo.get("isExpired"));
            mav.addObject("daysRemaining", expiryInfo.get("daysRemaining"));
            mav.addObject("hoursRemaining", expiryInfo.get("hoursRemaining"));
            mav.addObject("minutesRemaining", expiryInfo.get("minutesRemaining"));
            mav.addObject("formattedExpiryDate", expiryInfo.get("formattedExpiryDate"));
            
            return mav;
            
        } catch (EmptyResultDataAccessException e) {
            // Block doesn't exist in database
            mav.setViewName("error");
            mav.addObject("errorMessage", "Block with address '" + address + "' not found.");
            return mav;
        } catch (Exception e) {
            // Any other error
            mav.setViewName("error");
            mav.addObject("errorMessage", "Error loading block information: " + e.getMessage());
        
    //Checking if its a manager
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    boolean isManager = auth != null && auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_MANAGER"));
    mav.addObject("isManager", isManager);

    return mav;
        }
    }
    
    @GetMapping("/Test")
    public ModelAndView Links(){
        ModelAndView mavv = new ModelAndView("Links.html");
        return mavv;
    }

@PostMapping("/OrganisationInfo/report")
public String reportBlock(@RequestParam int id, @RequestParam String name, @RequestParam String reason) {
    organisationInfoRepository.reportBlock(id, name, reason);
    return "redirect:/Index";
}
}
package com.community.communication.OrganisationInfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.community.communication.Approved.Block;

@Controller
public class OrganisationInfoController {
@Autowired
private OrganisationInfoRepository organisationInfoRepository;
@GetMapping("/OrganisationInfo/{address}")
public ModelAndView OrganisationInfoView(@PathVariable String address){
    ModelAndView mav = new ModelAndView("OrganisationInfo.html");
    Block Info = organisationInfoRepository.getInfo(address);
    mav.addObject("Info", Info);

    //Checking if its a manager
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    boolean isManager = auth != null && auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_MANAGER"));
    mav.addObject("isManager", isManager);

    return mav;
}
@GetMapping("/Test")
public ModelAndView Links(){
    ModelAndView mavv = new ModelAndView("Links.html");
    return mavv;
}

@PostMapping("/OrganisationInfo/report")
public String reportBlock(@RequestParam int id, @RequestParam String name, @RequestParam String reason, RedirectAttributes redirectAttributes) {
    organisationInfoRepository.reportBlock(id, name, reason);
    redirectAttributes.addFlashAttribute("successMessage", "The block is reported, and it has been moved to the approval page for review.");
    return "redirect:/Index";
}
}

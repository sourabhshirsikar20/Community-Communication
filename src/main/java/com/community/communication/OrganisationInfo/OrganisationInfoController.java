package com.community.communication.OrganisationInfo;

import com.community.communication.Approved.Block;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class OrganisationInfoController {
@Autowired
private OrganisationInfoRepository organisationInfoRepository;
@GetMapping("/OrganisationInfo/{Id}")
public ModelAndView OrganisationInfoView(@PathVariable int Id){
    ModelAndView mav = new ModelAndView("OrganisationInfo.html");
    Block Info = organisationInfoRepository.getInfo(Id);
    mav.addObject("Info", Info);
    return mav;
}
@GetMapping("/Test")
public ModelAndView Links(){
    ModelAndView mavv = new ModelAndView("Links.html");
    return mavv;
}
}

package com.community.communication.OrganisationInfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class OrganisationInfoController {
@Autowired
private OrganisationInfoRepository organisationInfoRepository;
@GetMapping("/OrganisationInfo")
public ModelAndView OrganisationInfoView(){
    ModelAndView mav = new ModelAndView("OrganisationInfo.html");
    List<OrganisationInfo> Info = organisationInfoRepository.getAllInfo();
    mav.addObject("Info", Info);
    return mav;
}
}

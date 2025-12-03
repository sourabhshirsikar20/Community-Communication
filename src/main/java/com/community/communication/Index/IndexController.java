package com.community.communication.Index;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.community.communication.Approved.ApproverRepository;
import com.community.communication.Approved.Block;
import com.community.communication.Approved.StreetInfo;
import com.community.communication.OrganisationInfo.OrganisationInfoRepository;

@Controller
public class IndexController {

    @Autowired
    private ApproverRepository ApproverRepository;
    @GetMapping("/")
    public String redirectToIndex() {
        return "redirect:/Index";
    }

    @Autowired
    private OrganisationInfoRepository OrganisationInfoRepository;

     @GetMapping("/Index")
    public ModelAndView ApprovedList(){
        ModelAndView mav = new ModelAndView("StreetView.html");
        List<Block> Approved = ApproverRepository.getStreetBlocks("");
        mav.addObject("Approved", Approved);
        return mav;
    }
    
    @GetMapping("/street/{street}")
    public ModelAndView Street(@PathVariable String street){
        ModelAndView mav = new ModelAndView("StreetView.html");
        
        if (street.equals("root"))
            street = "";
        
        List<Block> Approved = ApproverRepository.getStreetBlocks(street);
        mav.addObject("Approved", Approved);

        return mav;
    }

    @ResponseBody
    @GetMapping(path="/street-info/{street}", produces=MediaType.APPLICATION_JSON_VALUE)
    public StreetInfo getStreetInfo(@PathVariable String street) {
        StreetInfo info = new StreetInfo(
            street.length() <= 2 ? 
                ApproverRepository.getStreet("root") : 
                ApproverRepository.getStreet(street.substring(0, street.length()-2)), 
            ApproverRepository.getStreet(street),
            ApproverRepository.getSubStreets(street)
        );
        return info;
    }

    @ResponseBody
    @GetMapping("/block-name/{address}")
    public String getBlockName(@PathVariable String address) {
        return OrganisationInfoRepository.getInfo(address).getName();
    }
    

    @GetMapping("/search")
    public ModelAndView submitSearch(@RequestParam("search_value") String search_value) { 
        ModelAndView mav = new ModelAndView("StreetView.html");
        List<Block> Approved = ApproverRepository.getSearch(search_value); 
        mav.addObject("Approved", Approved);
        return mav; 
    }

    @GetMapping("/live-search")
    @ResponseBody
    public List<Block> searchApi(@RequestParam("value") String value) {
        return ApproverRepository.getSearch(value);
    }
}
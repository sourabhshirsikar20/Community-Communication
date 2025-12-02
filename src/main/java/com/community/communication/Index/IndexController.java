package com.community.communication.Index;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
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
        mav.addObject("Approved", Approved);
        return mav;
    }


    @GetMapping("/street")
    public ModelAndView MainStreet() {
        return Street("00");
    }
    
    @GetMapping("/street/{street}")
    public ModelAndView Street(@PathVariable String street){
        ModelAndView mav = new ModelAndView("StreetView.html");

        List<Block> Approved = ApproverRepository.getApprovedBlocks();
        mav.addObject("Approved", Approved);

        return mav;
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

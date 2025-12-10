package com.community.communication.Promos;

import com.community.communication.Approved.Block;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;

@Controller
public class PromoController {

    @Autowired
    private PromoRepository promoRepository;

    @GetMapping("/Promos/Manage")
    public ModelAndView managePromos() {
        ModelAndView mav = new ModelAndView("ManagePromos.html");
        
        
        Map<Integer, Block> currentPromos = promoRepository.getActivePromos();
        
       
        List<Block> allBlocks = promoRepository.getAllBlocks();

        mav.addObject("promos", currentPromos);
        mav.addObject("blocks", allBlocks);
        return mav;
    }

    @PostMapping("/Promos/Set")
    public String setPromo(@RequestParam int blockId, @RequestParam int position) {
        if (position >= 1 && position <= 4) {
            promoRepository.setPromo(blockId, position);
        }
        return "redirect:/Promos/Manage";
    }

    @PostMapping("/Promos/Remove")
    public String removePromo(@RequestParam int position) {
        promoRepository.removePromo(position);
        return "redirect:/Promos/Manage";
    }
}
package com.community.communication.SubmitBlock;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class SubmitController {

    @GetMapping("/SubmitBlock")
    public ModelAndView SubmitBlockPage(){
        ModelAndView mav = new ModelAndView("SubmitBlock.html");
        return mav;
    }
}

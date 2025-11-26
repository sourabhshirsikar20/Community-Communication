package com.community.communication.OrganiseBlock;

import com.community.communication.Approved.Block;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/Block")
public class BlockController {
    @Autowired
    private BlockRepository blockRepository;

    @GetMapping("/View")
    public ModelAndView ViewBlock(@RequestParam(value = "Address", required = false) String Address){
        ModelAndView mav = new ModelAndView("BlockView");
        boolean Exists = false;
        if(Address != null && !Address.isEmpty()){
            Exists = blockRepository.Check(Address);
            if(Exists) {
                Block block = blockRepository.ByAddress(Address);
                mav.addObject("Block", block);
            }
        }
        mav.addObject("Exists", Exists);
        mav.addObject("Address", Address);
        return mav;
    }
    @GetMapping("/Check")
    public ModelAndView CheckB(@RequestParam(value = "Address", required = false) String Address){
        ModelAndView mav = new ModelAndView("BlockCheck");
        if(Address!=null && !Address.isEmpty()){
            boolean Exists = blockRepository.Check(Address);
            mav.addObject("Address", Address);
            mav.addObject("Exists", Exists);
        }
        return mav;
    }
    @GetMapping("/Add")
    public ModelAndView Add(){
        ModelAndView mav= new ModelAndView("BlockAdd");
        return mav;
    }
    @PostMapping("/Add")
    public ModelAndView Adding(@RequestParam String Address,@RequestParam String Name,@RequestParam int RoofOption,@RequestParam String RoofColour,@RequestParam int BaseOption,@RequestParam String BaseColour,@RequestParam int ForegroundOption,@RequestParam String ForegroundColour,@RequestParam String MainText,@RequestParam String LinksText,@RequestParam String InfoText,@RequestParam String PromoText){
        Block block = new Block();
        block.setAddress(Address);
        block.setName(Name);
        block.setRoofOption(RoofOption);
        block.setRoofColour(RoofColour);
        block.setBaseOption(BaseOption);
        block.setBaseColour(BaseColour);
        block.setForegroundOption(ForegroundOption);
        block.setForegroundColour(ForegroundColour);
        block.setMainText(MainText);
        block.setLinksText(LinksText);
        block.setInfoText(InfoText);
        block.setPromoText(PromoText);
        blockRepository.Add(block);
        ModelAndView mav= new ModelAndView("BlockAdd");
        mav.addObject("Str", "Added");
        return mav;
    }
    @GetMapping("/Update/{Address}")
    public ModelAndView Update(@PathVariable String Address){
        ModelAndView mav = new ModelAndView("BlockUpdate");
        Block block = null;
        if(Address!=null && !Address.isEmpty() && blockRepository.Check(Address)){
            block = blockRepository.ByAddress(Address);
        }
        else{
            mav.addObject("Str","Does Not Exists Cannot Update!");
        }
        mav.addObject("Block", block);
        return mav;
    }
    @PostMapping("/Update")
    public ModelAndView Updating(@RequestParam int Id, @RequestParam String Address,@RequestParam String Name,@RequestParam int RoofOption,@RequestParam String RoofColour,@RequestParam int BaseOption,@RequestParam String BaseColour,@RequestParam int ForegroundOption,@RequestParam String ForegroundColour,@RequestParam String MainText,@RequestParam String LinksText,@RequestParam String InfoText,@RequestParam String PromoText){
        ModelAndView mav = new ModelAndView("BlockUpdate");
        Block Exists = blockRepository.ById(Id);
        if(Exists == null){
            mav.addObject("Str", "Cannot Update Does not Exists");
            mav.addObject("Block", null);
            return mav;
        }
            Block block = new Block();
            block.setId(Id);
            block.setAddress(Address);
            block.setName(Name);
            block.setRoofOption(RoofOption);
            block.setRoofColour(RoofColour);
            block.setBaseOption(BaseOption);
            block.setBaseColour(BaseColour);
            block.setForegroundOption(ForegroundOption);
            block.setForegroundColour(ForegroundColour);
            block.setMainText(MainText);
            block.setLinksText(LinksText);
            block.setInfoText(InfoText);
            block.setPromoText(PromoText);
            if(blockRepository.Check(Address)){
                mav.addObject("Str", "Cannot Update Into Existing Address");
                mav.addObject("Block", null);
                return mav;
            }
            blockRepository.Update(block);
            mav.addObject("Str", "Updated Successfully");
            mav.addObject("Block", block);
        return mav;
    }
}

package com.community.communication.OrganiseBlock;

import com.community.communication.Approved.Block;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    public ModelAndView Adding(@RequestParam String Address,@RequestParam String Name,@RequestParam int RoofOption,@RequestParam String RoofColour,@RequestParam int BaseOption,@RequestParam String BaseColour,@RequestParam int ForegroundOption,@RequestParam String ForegroundColour,@RequestParam String FontOptions,@RequestParam String FontColour,@RequestParam String MainText,@RequestParam String LinksText,@RequestParam String InfoText,@RequestParam String PromoText, @RequestParam String SecretHash){
        ModelAndView mav= new ModelAndView("BlockAdd");
        mav.addObject("Address", Address);
        mav.addObject("Name", Name);
        mav.addObject("RoofOption", RoofOption);
        mav.addObject("RoofColour", RoofColour);
        mav.addObject("BaseOption", BaseOption);
        mav.addObject("BaseColour", BaseColour);
        mav.addObject("ForegroundOption", ForegroundOption);
        mav.addObject("ForegroundColour", ForegroundColour);
        mav.addObject("FontOptions", FontOptions);
        mav.addObject("FontColour", FontColour);
        mav.addObject("MainText", MainText);
        mav.addObject("LinksText", LinksText);
        mav.addObject("MainText", MainText);
        mav.addObject("InfoText", InfoText);
        mav.addObject("PromoText", PromoText);
        mav.addObject("SecretHash", SecretHash);
        boolean CheckEmpty = false;
        if(Address == null || Address.isBlank()){ mav.addObject("AddressErr", "Please Enter Address"); CheckEmpty = true; }
        if(Name == null || Name.isBlank()){ mav.addObject("NameErr", "Please Enter Name"); CheckEmpty = true; }
        if(RoofColour == null || RoofColour.isBlank()){ mav.addObject("RoofColourErr", "Please Enter Roof Colour"); CheckEmpty = true; }
        if(BaseColour == null || BaseColour.isBlank()){ mav.addObject("BaseColourErr", "Please Enter Base Colour"); CheckEmpty = true; }
        if(ForegroundColour == null || ForegroundColour.isBlank()){ mav.addObject("ForegroundColourErr", "Please Enter Foreground Colour"); CheckEmpty = true; }
        if(FontOptions == null || FontOptions.isBlank()){ mav.addObject("FontOptionsErr", "Please Enter Font Options"); CheckEmpty = true; }
        if(FontColour == null || FontColour.isBlank()){ mav.addObject("FontColourErr", "Please Enter Font Colour"); CheckEmpty = true; }
        if(MainText == null || MainText.isBlank()){ mav.addObject("MainTextErr", "Please Enter Main Text"); CheckEmpty = true; }
        if(LinksText == null || LinksText.isBlank()){ mav.addObject("LinksTextErr", "Please Enter Links Text"); CheckEmpty = true; }
        if(InfoText == null || InfoText.isBlank()){ mav.addObject("InfoTextErr", "Please Enter Info Text"); CheckEmpty = true; }
        if(PromoText == null || PromoText.isBlank()){ mav.addObject("PromoTextErr", "Please Enter Promo Text"); CheckEmpty = true; }
        if(SecretHash == null || SecretHash.isBlank()){ mav.addObject("SecretHashErr", "Please Enter Secret Hash"); CheckEmpty = true; }
        if(CheckEmpty){
            return mav;
        }
        Block block = new Block();
        block.setAddress(Address);
        block.setName(Name);
        block.setRoofOption(RoofOption);
        block.setRoofColour(RoofColour);
        block.setBaseOption(BaseOption);
        block.setBaseColour(BaseColour);
        block.setForegroundOption(ForegroundOption);
        block.setForegroundColour(ForegroundColour);
        block.setFontOptions(FontOptions);
        block.setFontColour(FontColour);
        block.setMainText(MainText);
        block.setLinksText(LinksText);
        block.setInfoText(InfoText);
        block.setPromoText(PromoText);
        block.setSecretHash(SecretHash);
        if(blockRepository.Check(Address)){
            mav.addObject("Str", "Cannot Add Into Existing Address");
            mav.addObject("Block", null);
            return mav;
        }
        blockRepository.Add(block);
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
    public ModelAndView Updating(@RequestParam int Id, @RequestParam String Address,@RequestParam String Name,@RequestParam int RoofOption,@RequestParam String RoofColour,@RequestParam int BaseOption,@RequestParam String BaseColour,@RequestParam int ForegroundOption,@RequestParam String ForegroundColour,@RequestParam String FontOptions, @RequestParam String FontColour,@RequestParam String MainText,@RequestParam String LinksText,@RequestParam String InfoText,@RequestParam String PromoText, @RequestParam String SecretHash){
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
            block.setFontOptions(FontOptions);
            block.setFontColour(FontColour);
            block.setMainText(MainText);
            block.setLinksText(LinksText);
            block.setInfoText(InfoText);
            block.setPromoText(PromoText);
            block.setApproved(Exists.isApproved());
            block.setSecretHash(SecretHash);
            boolean CheckEmpty = false;
            if(Address == null || Address.isBlank()){ mav.addObject("AddressErr", "Please Enter Address"); CheckEmpty = true; }
            if(Name == null || Name.isBlank()){ mav.addObject("NameErr", "Please Enter Name"); CheckEmpty = true; }
            if(RoofColour == null || RoofColour.isBlank()){ mav.addObject("RoofColourErr", "Please Enter Roof Colour"); CheckEmpty = true; }
            if(BaseColour == null || BaseColour.isBlank()){ mav.addObject("BaseColourErr", "Please Enter Base Colour"); CheckEmpty = true; }
            if(ForegroundColour == null || ForegroundColour.isBlank()){ mav.addObject("ForegroundColourErr", "Please Enter Foreground Colour"); CheckEmpty = true; }
            if(FontOptions == null || FontOptions.isBlank()){ mav.addObject("FontOptionsErr", "Please Enter Font Options"); CheckEmpty = true; }
            if(FontColour == null || FontColour.isBlank()){ mav.addObject("FontColourErr", "Please Enter Font Colour"); CheckEmpty = true; }
            if(MainText == null || MainText.isBlank()){ mav.addObject("MainTextErr", "Please Enter Main Text"); CheckEmpty = true; }
            if(LinksText == null || LinksText.isBlank()){ mav.addObject("LinksTextErr", "Please Enter Links Text"); CheckEmpty = true; }
            if(InfoText == null || InfoText.isBlank()){ mav.addObject("InfoTextErr", "Please Enter Info Text"); CheckEmpty = true; }
            if(PromoText == null || PromoText.isBlank()){ mav.addObject("PromoTextErr", "Please Enter Promo Text"); CheckEmpty = true; }
            if(SecretHash == null || SecretHash.isBlank()){ mav.addObject("SecretHashErr", "Please Enter Secret Hash"); CheckEmpty = true; }
            if(CheckEmpty){
                mav.addObject("Block", block);
                return mav;
            }
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
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ModelAndView IntCheck() {
        ModelAndView mav = new ModelAndView("BlockAdd");

        mav.addObject("RoofOptionErr", "Enter Roof Option / Must be a Number");
        mav.addObject("BaseOptionErr", "Enter Base Option / Must be a Number");
        mav.addObject("ForegroundOptionErr", "Enter Foreground Option / Must be a Number");

        return mav;
    }
    @PostMapping("/block/delete")
    public String deleteBlock(@RequestParam int blockId, RedirectAttributes redirectAttributes) {

        blockRepository.deleteBlock(blockId);

        redirectAttributes.addFlashAttribute("successMessage", "Block was successfully deleted.");

        return "redirect:/Index";
    }
    @GetMapping("/DownloadJSON")
    public ResponseEntity<byte[]> DownloadJSON(@RequestParam String Address) throws Exception{
        if(Address == null || Address.isEmpty() || !blockRepository.Check(Address)){
            String Error = "{\"Error\": \"Address Not Found\"}";
            return ResponseEntity.badRequest().header("Content-Disposition","attachment; filename=\"InvalidAddress.json\"").body(Error.getBytes());
        }
        Block block = blockRepository.ByAddress(Address);
        ObjectMapper Map = new ObjectMapper();
        String JSON = Map.writerWithDefaultPrettyPrinter().writeValueAsString(block);
        return ResponseEntity.ok().header("Content-Disposition","attachment; filename=\"Block_"+ Address + ".json\"").body(JSON.getBytes());
    }
}

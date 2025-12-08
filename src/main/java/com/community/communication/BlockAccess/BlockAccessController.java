package com.community.communication.BlockAccess;

import com.community.communication.Approved.Block;
import com.community.communication.OrganiseBlock.BlockRepository;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/BlockAccess")
public class BlockAccessController {

    private final BlockRepository blockRepository;
    private final PasswordEncoder passwordEncoder;

    public BlockAccessController(BlockRepository blockRepository,
                                 PasswordEncoder passwordEncoder) {
        this.blockRepository = blockRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping
    public ModelAndView showAccessForm(@RequestParam(value = "address", required = false) String address) {
        ModelAndView mav = new ModelAndView("BlockAccess.html");
        mav.addObject("address", address);
        return mav;
    }

    @PostMapping
    public ModelAndView checkCode(@RequestParam String address,
                                  @RequestParam String accessCode) {
        ModelAndView mav = new ModelAndView("BlockAccess.html");
        mav.addObject("address", address);

        try {
            Block block = blockRepository.ByAddress(address);

            if (block.getSecretHash() == null ||
                    !passwordEncoder.matches(accessCode, block.getSecretHash())) {
                mav.addObject("error", "Wrong access code or Access code has not generated yet.");
                return mav;
            }


            ModelAndView edit = new ModelAndView("BlockAccessEdit.html");
            edit.addObject("block", block);
            edit.addObject("accessCode", accessCode);
            return edit;
        } catch (EmptyResultDataAccessException ex) {
            mav.addObject("error", "Block with address " + address + " does not exist.");
            return mav;
        }
    }


    @PostMapping("/save")
    public ModelAndView saveChanges(@RequestParam int id,
                                    @RequestParam String accessCode,
                                    @RequestParam String address,
                                    @RequestParam String name,
                                    @RequestParam int roofOption,
                                    @RequestParam String roofColour,
                                    @RequestParam int baseOption,
                                    @RequestParam String baseColour,
                                    @RequestParam int foregroundOption,
                                    @RequestParam String foregroundColour,
                                    @RequestParam String mainText,
                                    @RequestParam String linksText,
                                    @RequestParam String infoText,
                                    @RequestParam String promoText) {

        Block current = blockRepository.ById(id);

        ModelAndView mav = new ModelAndView("BlockAccessEdit.html");

        try {
            Block existing = blockRepository.ByAddress(address);

            if (existing != null && existing.getId() != id) {
                mav.addObject("error", "Address already exists. Changes NOT saved.");
                mav.addObject("block", current);
                mav.addObject("accessCode", accessCode);
                return mav;
            }

        } catch (EmptyResultDataAccessException ignored) {}

        Block updated = new Block();
        updated.setId(id);
        updated.setAddress(address);
        updated.setName(name);
        updated.setRoofOption(roofOption);
        updated.setRoofColour(roofColour);
        updated.setBaseOption(baseOption);
        updated.setBaseColour(baseColour);
        updated.setForegroundOption(foregroundOption);
        updated.setForegroundColour(foregroundColour);
        updated.setMainText(mainText);
        updated.setLinksText(linksText);
        updated.setInfoText(infoText);
        updated.setPromoText(promoText);

        updated.setApproved(current.isApproved());
        updated.setSecretHash(current.getSecretHash());

        blockRepository.Update(updated);

        mav.addObject("block", updated);
        mav.addObject("accessCode", accessCode);
        mav.addObject("success", "Modifications Saved Successfully!.");
        return mav;
    }
    @PostMapping("/updateCode")
    public ModelAndView updateCode(@RequestParam int id,
                                   @RequestParam(required = false) String newAccessCode,
                                   @RequestParam(required = false) String confirmAccessCode,
                                   @RequestParam String accessCode) {

        ModelAndView mav = new ModelAndView("BlockAccessEdit.html");
        Block current = blockRepository.ById(id);

        mav.addObject("block", current);
        mav.addObject("accessCode", accessCode);

        if (current == null ||
                current.getSecretHash() == null ||
                !passwordEncoder.matches(accessCode, current.getSecretHash())) {
            mav.addObject("error", "Invalid access code! No changes saved.");
            return mav;
        }

        if (newAccessCode == null || newAccessCode.isBlank()) {
            mav.addObject("success", "Updated (access code unchanged).");
            return mav;
        }

        if (!newAccessCode.matches("\\d{4,6}")) {
            mav.addObject("error", "Code must be 4–6 digits.");
            return mav;
        }

        if (!newAccessCode.equals(confirmAccessCode)) {
            mav.addObject("error", "Codes do NOT match!");
            return mav;
        }
        AccessCodeValidator validator = new AccessCodeValidator();
        if (!validator.isValid(newAccessCode, confirmAccessCode)) {
            mav.addObject("error", "Codes must match and contain only 4–6 digits.");
            return mav;
        }

        String hash = passwordEncoder.encode(newAccessCode);
        current.setSecretHash(hash);
        blockRepository.Update(current);

        mav.addObject("success", "Access code updated successfully!");
        return mav;
    }

    @GetMapping("/checkAddress")
    @ResponseBody
    public boolean checkAddress(@RequestParam String value) {
        return blockRepository.Check(value);
    }
}

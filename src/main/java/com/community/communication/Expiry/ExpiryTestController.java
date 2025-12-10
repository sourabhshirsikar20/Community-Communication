package com.community.communication.Expiry;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.community.communication.Approved.Block;
import com.community.communication.OrganiseBlock.BlockRepository;

/**
 * Controller for testing expiry functionality Access these URLs to test:
 * http://localhost:8080/test/expiry-status (HTML Dashboard) -
 * http://localhost:8080/api/expiry-status (JSON API) -
 * http://localhost:8080/test/trigger-cleanup (Manual cleanup button)
 */

@Controller
public class ExpiryTestController {

    @Autowired
    private BlockRepository blockRepository;

    @Autowired
    private ExpiryService expiryService;

    @Autowired
    private ExpiryCleanupService cleanupService;


    @GetMapping("/test/expiry-status")
    public ModelAndView showExpiryStatus() {
        ModelAndView mav = new ModelAndView("ExpiryTest");

        // Get ALL blocks from database
        List<Block> allBlocks = blockRepository.findAll();

        List<Block> approvedBlocksWithTimer = allBlocks.stream()
                .filter(block -> block.isApproved()) // Must be approved
                .filter(block -> block.getCreatedDate() != null) // Must have timer started
                .collect(Collectors.toList());

        // Add expiry info to each approved block
        List<Map<String, Object>> blocksWithExpiry = approvedBlocksWithTimer.stream()
                .map(block -> {
                    Map<String, Object> blockInfo = new HashMap<>();
                    blockInfo.put("id", block.getId());
                    blockInfo.put("name", block.getName());
                    blockInfo.put("address", block.getAddress());
                    blockInfo.put("createdDate", block.getCreatedDate());

                    Map<String, Object> expiryInfo = expiryService.getExpiryInfo(block);
                    blockInfo.putAll(expiryInfo);

                    return blockInfo;
                })
                .collect(Collectors.toList());

        mav.addObject("blocks", blocksWithExpiry);
        return mav;
    }

    
    @GetMapping("/api/expiry-status")
    @ResponseBody
    public Map<String, Object> getExpiryStatusJson() {
        List<Block> allBlocks = blockRepository.findAll();

        List<Block> approvedBlocks = allBlocks.stream()
                .filter(block -> block.isApproved())
                .filter(block -> block.getCreatedDate() != null)
                .collect(Collectors.toList());

        Map<String, Object> response = new HashMap<>();
        response.put("totalBlocks", approvedBlocks.size());

        long expiredCount = approvedBlocks.stream()
                .filter(expiryService::isBlockExpired)
                .count();

        response.put("expiredBlocks", expiredCount);
        response.put("activeBlocks", approvedBlocks.size() - expiredCount);

        List<Map<String, Object>> blockDetails = approvedBlocks.stream()
                .map(block -> {
                    Map<String, Object> info = new HashMap<>();
                    info.put("id", block.getId());
                    info.put("name", block.getName());
                    info.put("address", block.getAddress());
                    info.put("createdDate", block.getCreatedDate());
                    info.put("approved", block.isApproved());
                    info.put("isExpired", expiryService.isBlockExpired(block));
                    info.put("daysRemaining", ExpiryCalculator.getDaysRemaining(block.getCreatedDate()));
                    return info;
                })
                .collect(Collectors.toList());

        response.put("blocks", blockDetails);
        response.put("timestamp", java.time.LocalDateTime.now().toString());

        return response;
    }

    @PostMapping("/test/trigger-cleanup")
    @ResponseBody
    public Map<String, Object> triggerManualCleanup() {
        int deletedCount = cleanupService.manualCleanup();

        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("deletedCount", deletedCount);
        response.put("message", "Manual cleanup completed. Deleted " + deletedCount + " expired block(s).");
        response.put("timestamp", java.time.LocalDateTime.now().toString());

        return response;
    }

    /**
     * OPTIONAL: Quick status check - how many blocks are expired right now? Access at:
     * http://localhost:8080/api/expired-count
     */
    @GetMapping("/api/expired-count")
    @ResponseBody
    public Map<String, Object> getExpiredCount() {
        List<Block> allBlocks = blockRepository.findAll();

        List<Block> approvedBlocks = allBlocks.stream()
                .filter(block -> block.isApproved())
                .filter(block -> block.getCreatedDate() != null)
                .collect(Collectors.toList());

        int expiredCount = (int) approvedBlocks.stream()
                .filter(expiryService::isBlockExpired)
                .count();

        Map<String, Object> response = new HashMap<>();
        response.put("expiredCount", expiredCount);
        response.put("totalApprovedBlocks", approvedBlocks.size());
        response.put("activeCount", approvedBlocks.size() - expiredCount);
        response.put("timestamp", java.time.LocalDateTime.now().toString());

        return response;
    }
}

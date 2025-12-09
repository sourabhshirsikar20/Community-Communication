package com.community.communication.Expiry;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.community.communication.Approved.Block;
import com.community.communication.OrganiseBlock.BlockRepository;

/**
 * Service that automatically deletes expired blocks from the database. Works with your JDBC BlockRepository implementation.
 */
@Service
public class ExpiryCleanupService {

    private static final Logger logger = LoggerFactory.getLogger(ExpiryCleanupService.class);

    @Autowired
    private BlockRepository blockRepository; // Your JDBC repository

    @Autowired
    private ExpiryService expiryService;

    /**
     * AUTOMATIC CLEANUP - Runs every 2 minutes This method runs automatically
     * while your server is running. It checks all APPROVED blocks and deletes
     * expired ones. Change the timing by modifying fixedRate value: - 120000 =
     * 2 minutes 300000 = 5 minutes 3600000 = 1 hour
     */
    @Scheduled(fixedRate = 120000, initialDelay = 10000)
    @Transactional
    public void deleteExpiredBlocks() {
        logger.info("========================================");
        logger.info("Starting expired blocks cleanup task...");
        logger.info("========================================");

        try {
            // Get all blocks from database
            List<Block> allBlocks = blockRepository.findAll();
            logger.info("Total blocks in database: {}", allBlocks.size());

            int deletedCount = 0;
            int activeCount = 0;
            int unapprovedCount = 0;
            int noDateCount = 0;

            // Check each block for expiry
            for (Block block : allBlocks) {
                // Skip unapproved blocks - they don't have expiry timers
                if (!block.isApproved()) {
                    unapprovedCount++;
                    continue;
                }

                // Skip blocks without created_date (shouldn't happen for approved blocks)
                if (block.getCreatedDate() == null) {
                    logger.warn("ANOMALY: Approved block {} ('{}') has null created_date!",
                            block.getId(), block.getName());
                    noDateCount++;
                    continue;
                }

                // Check if expired
                boolean isExpired = expiryService.isBlockExpired(block);

                if (isExpired) {
                    logger.info("DELETING expired block:");
                    logger.info("   - ID: {}", block.getId());
                    logger.info("   - Name: {}", block.getName());
                    logger.info("   - Address: {}", block.getAddress());
                    logger.info("   - Created: {}", block.getCreatedDate());

                    blockRepository.delete(block);
                    deletedCount++;
                } else {
                    activeCount++;
                }
            }

            // Summary log
            logger.info("========================================");
            logger.info("Cleanup completed successfully!");
            logger.info("Active approved blocks: {}", activeCount);
            logger.info("Deleted expired blocks: {}", deletedCount);
            logger.info("Unapproved blocks: {}", unapprovedCount);
            if (noDateCount > 0) {
                logger.warn("Approved blocks with missing created_date: {}", noDateCount);
            }
            logger.info("========================================");

        } catch (Exception e) {
            logger.error("========================================");
            logger.error("ERROR during expired blocks cleanup!");
            logger.error("Error message: {}", e.getMessage());
            logger.error("========================================", e);
        }
    }

    /**
     * MANUAL CLEANUP - Call this from a controller or admin panel This method
     * can be triggered manually by an admin clicking a button. Returns the
     * number of blocks deleted.
     *
     * Only deletes APPROVED blocks that are expired.
     */
    @Transactional
    public int manualCleanup() {
        logger.info("========================================");
        logger.info("MANUAL cleanup triggered by admin");
        logger.info("========================================");

        List<Block> allBlocks = blockRepository.findAll();
        int deletedCount = 0;
        int skippedUnapproved = 0;

        for (Block block : allBlocks) {
            // Only process approved blocks
            if (!block.isApproved()) {
                skippedUnapproved++;
                continue;
            }

            if (block.getCreatedDate() != null && expiryService.isBlockExpired(block)) {
                logger.info("Deleting expired block: {} (ID: {})", block.getName(), block.getId());
                blockRepository.delete(block);
                deletedCount++;
            }
        }

        logger.info("========================================");
        logger.info("Manual cleanup completed. Deleted {} expired block(s)", deletedCount);
        logger.info("Skipped {} unapproved block(s)", skippedUnapproved);
        logger.info("========================================");

        return deletedCount;
    }

    /**
     * Get count of expired blocks WITHOUT deleting them Useful for dashboards
     * or monitoring
     *
     * Only counts APPROVED blocks that are expired.
     */
    public int countExpiredBlocks() {
        List<Block> allBlocks = blockRepository.findAll();
        int expiredCount = 0;

        for (Block block : allBlocks) {
            // Only count approved blocks
            if (block.isApproved()
                    && block.getCreatedDate() != null
                    && expiryService.isBlockExpired(block)) {
                expiredCount++;
            }
        }

        return expiredCount;
    }
}

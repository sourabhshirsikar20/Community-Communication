package com.community.communication.Expiry;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.joda.time.DateTime;
import org.springframework.stereotype.Service;

import com.community.communication.Approved.Block;

/**
 * Service class for managing block expiry logic.
 * Separates business logic from controllers.
 */
@Service
public class ExpiryService {

    // Gets complete expiry information for a block. Returns a map with all expiry details.
    public Map<String, Object> getExpiryInfo(Block block) {
        Map<String, Object> expiryInfo = new HashMap<>();

        LocalDateTime createdDate = block.getCreatedDate();

        // Calculate expiry date
        DateTime expiryDate = ExpiryCalculator.calculateExpiryDate(createdDate);

        // Check if expired
        boolean isExpired = ExpiryCalculator.isExpired(createdDate);

        // Calculate remaining time
        long daysRemaining = ExpiryCalculator.getDaysRemaining(createdDate);
        long hoursRemaining = ExpiryCalculator.getHoursRemaining(createdDate);
        long minutesRemaining = ExpiryCalculator.getMinutesRemaining(createdDate);

        // Format expiry date
        String formattedExpiry = ExpiryCalculator.formatExpiryDate(expiryDate);

        // Put everything in map
        expiryInfo.put("expiryDate", expiryDate);
        expiryInfo.put("isExpired", isExpired);
        expiryInfo.put("daysRemaining", daysRemaining);
        expiryInfo.put("hoursRemaining", hoursRemaining);
        expiryInfo.put("minutesRemaining", minutesRemaining);
        expiryInfo.put("formattedExpiryDate", formattedExpiry);

        return expiryInfo;
    }

    //Quick check if a block is expired.

    public boolean isBlockExpired(Block block) {
        if (block == null || block.getCreatedDate() == null) {
            return false;
        }

        return ExpiryCalculator.isExpired(block.getCreatedDate());
    }
}

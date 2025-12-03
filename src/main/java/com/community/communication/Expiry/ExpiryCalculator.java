package com.community.communication.Expiry;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.Hours;
import org.joda.time.Minutes;

/**
 * Utility class for calculating block expiry dates and remaining time.
 * Uses Joda-Time for better date handling & Apache Commons for validation.
 */
public class ExpiryCalculator {

    private static final int EXPIRY_MONTHS = 6;

    // Calculates the expiry date (6 months from creation date).
    public static DateTime calculateExpiryDate(LocalDateTime createdDate) {
        if (createdDate == null) {
            throw new IllegalArgumentException("Created date cannot be null");
        }

        // Convert LocalDateTime to Joda DateTime
        Date date = Date.from(createdDate.atZone(ZoneId.systemDefault()).toInstant());
        DateTime jodaDate = new DateTime(date);
        return jodaDate.plusMonths(EXPIRY_MONTHS);
    }

    // Checks if a block has expired.
    public static boolean isExpired(LocalDateTime createdDate) {
        if (createdDate == null) {
            return false; // Treat null as not expired to avoid errors
        }

        DateTime expiryDate = calculateExpiryDate(createdDate);
        DateTime now = DateTime.now();

        return now.isAfter(expiryDate);
    }

    //Calculates days remaining until expiry.
    public static long getDaysRemaining(LocalDateTime createdDate) {
        if (createdDate == null || isExpired(createdDate)) {
            return 0;
        }

        DateTime expiryDate = calculateExpiryDate(createdDate);
        DateTime now = DateTime.now();

        return Days.daysBetween(now, expiryDate).getDays();
    }

    // Calculates hours remaining (after full days).
    public static long getHoursRemaining(LocalDateTime createdDate) {
        if (createdDate == null || isExpired(createdDate)) {
            return 0;
        }

        DateTime expiryDate = calculateExpiryDate(createdDate);
        DateTime now = DateTime.now();

        long totalHours = Hours.hoursBetween(now, expiryDate).getHours();
        return totalHours % 24;
    }

    // Calculates minutes remaining (after full hours).
    public static long getMinutesRemaining(LocalDateTime createdDate) {
        if (createdDate == null || isExpired(createdDate)) {
            return 0;
        }

        DateTime expiryDate = calculateExpiryDate(createdDate);
        DateTime now = DateTime.now();

        long totalMinutes = Minutes.minutesBetween(now, expiryDate).getMinutes();
        return totalMinutes % 60;
    }

    // Formats expiry date as readable string. Uses Apache Commons to safely handle null values.
    public static String formatExpiryDate(DateTime expiryDate) {
        if (expiryDate == null) {
            return "Unknown";
        }

        return expiryDate.toString("MMMM dd, yyyy hh:mm a");
    }

    // Validates if a date string is not blank. Uses Apache Commons Lang for StringUtils.
    public static boolean isValidDateString(String dateString) {
        return StringUtils.isNotBlank(dateString);
    }
}

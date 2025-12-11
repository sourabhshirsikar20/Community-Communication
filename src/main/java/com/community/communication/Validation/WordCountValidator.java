package com.community.communication.Validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class WordCountValidator implements ConstraintValidator<WordCount, String> {

    private int maxWords;

    @Override
    public void initialize(WordCount annotation) {
        this.maxWords = annotation.max();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        // Null or empty strings are valid (use @NotEmpty for required fields)
        if (value == null || value.trim().isEmpty()) {
            return true;
        }

        // Trim and split by whitespace
        String trimmed = value.trim();
        String[] words = trimmed.split("\\s+");

        // Check if word count exceeds maximum
        return words.length <= maxWords;
    }
}

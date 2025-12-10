package com.community.communication;

import com.community.communication.BlockAccess.AccessCodeValidator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AccessCodeValidatorTest {

        AccessCodeValidator validator = new AccessCodeValidator();

        @Test
        void testValidCodes() {
            assertTrue(validator.isValid("1234", "1234"));
            assertTrue(validator.isValid("987654", "987654"));
        }

        @Test
        void testCodesDoNotMatch() {
            assertFalse(validator.isValid("1234", "4321"));
        }

        @Test
        void testTooShortCode() {
            assertFalse(validator.isValid("123", "123"));
        }

        @Test
        void testTooLongCode() {
            assertFalse(validator.isValid("1234567", "1234567"));
        }

        @Test
        void testContainsLetters() {
            assertFalse(validator.isValid("12a4", "12a4"));
        }

        @Test
        void testNullInputs() {
            assertFalse(validator.isValid(null, "1234"));
            assertFalse(validator.isValid("1234", null));
        }
    }

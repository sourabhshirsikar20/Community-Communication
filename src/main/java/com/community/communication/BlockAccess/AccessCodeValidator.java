package com.community.communication.BlockAccess;

public class AccessCodeValidator {
    public boolean isValid(String newCode, String confirmCode) {
        if (newCode == null || confirmCode == null) {
            return false;
        }

        if (!newCode.equals(confirmCode)) {
            return false;
        }

        if (!newCode.matches("\\d{4,6}")) {
            return false;
        }

        return true;
    }
}

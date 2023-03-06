package com.upgrad.bookmyconsultation.exception;

import com.upgrad.bookmyconsultation.constants.UserConstants;

public enum SlotUnavailableErrorCode implements ErrorCode {

    SUE_001(UserConstants.SLOT_UNAVAILABLE_ERROR_CODE, UserConstants.SLOT_UNAVAILABLE_ERROR_MSG);
    private final String code;
    private final String defaultMessage;

    SlotUnavailableErrorCode(final String code, final String defaultMessage) {
        this.code = code;
        this.defaultMessage = defaultMessage;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getDefaultMessage() {
        return defaultMessage;
    }
}
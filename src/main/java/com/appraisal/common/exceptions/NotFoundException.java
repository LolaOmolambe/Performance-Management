package com.appraisal.common.exceptions;

import com.appraisal.common.enums.ResponseCode;

public class NotFoundException extends RuntimeException {
    private final String code;

    public NotFoundException() {
        super(ResponseCode.NOT_FOUND.getDescription());
        this.code = ResponseCode.NOT_FOUND.getCode();
    }

    public NotFoundException(ResponseCode responseCode) {
        super(responseCode.getDescription());
        this.code = responseCode.getCode();
    }

    public NotFoundException(String message) {
        super(message);
        this.code = ResponseCode.BAD_REQUEST.getCode();
    }

    public NotFoundException(String code, Throwable cause) {
        super(cause);
        this.code = code;
    }

    public String getCode() {
        return code;
    }

}

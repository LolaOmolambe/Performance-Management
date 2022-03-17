package com.appraisal.common.exceptions;

import com.appraisal.common.enums.ResponseCode;

public class BadRequestException extends RuntimeException {
    private final String code;

    public BadRequestException() {
        super(ResponseCode.BAD_REQUEST.getDescription());
        this.code = ResponseCode.BAD_REQUEST.getCode();
    }

    public BadRequestException(ResponseCode responseCode) {
        super(responseCode.getDescription());
        this.code = responseCode.getCode();
    }

    public BadRequestException(String message) {
        super(message);
        this.code = ResponseCode.BAD_REQUEST.getCode();
    }

    public BadRequestException(String code, Throwable cause) {
        super(cause);
        this.code = code;
    }

    public String getCode() {
        return code;
    }

}

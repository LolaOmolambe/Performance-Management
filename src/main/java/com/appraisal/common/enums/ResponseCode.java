package com.appraisal.common.enums;

public enum ResponseCode {

    // 400 Series
    BAD_REQUEST("4000", "The request could not be completed due to malformed syntax. Kindly crosscheck and try again."),
    DUPLICATE_EMAIL("4001", "An employee with this email already exists."),
    INVALID_MANAGER("4002", "Manager does not exist."),
    INVALID_EMPLOYEE("4003", "Employee does not exist."),
    EMPLOYEE_MANAGER_EXISTS("4004", "Employee already assigned to a manager."),
    NOT_FOUND("4005", "The requested resource does not exist."),
    MANAGER_EXISTS("4006", "A manager with this email already exists."),

    // 500 Series
    INTERNAL_SERVER_ERROR("5000", "An unexpected error occurred while processing your request. Please try again later.");

    private String code;

    private String description;

    ResponseCode() {
    }

    ResponseCode(String code, String description) {
        this.code = code;
        this.description = description;
    }

    public String getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }
}

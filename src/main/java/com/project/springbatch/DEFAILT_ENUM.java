package com.project.springbatch;

public enum DEFAILT_ENUM {
    COMPLETED("COMPLETED"),
    CUSTOM_EXIT_STATUS_CODE("CUSTOM_EXIT_STATUS_CODE"),
    FAILED("FAILED");

    private String value;

    DEFAILT_ENUM(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}

package com.droidshop.api.model.user;

public enum UserStatus {
    PENDING("P"), ACTIVE("A"), INACTIVE("I"), DELETED("D");

    private String statusCode;

    private UserStatus(String statusCode) {
        this.statusCode = statusCode;
    }

    public String getStatusCode() {
        return this.statusCode;
    }
}
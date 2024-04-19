package com.edu.serviceFarma.utils;

public class ValidationRequest {
    private String token;

    public ValidationRequest(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}

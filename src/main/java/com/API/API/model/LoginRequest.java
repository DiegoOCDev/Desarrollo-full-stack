package com.API.API.model;

public class LoginRequest {
    private String username;
    private String password;
    public LoginRequest(String username, String password) {
        this.username = username;
    }
    public String getUsername() {
        return username;
    }
    public void setpassword(String password) {
        this.password = password;
    }
    public String getPassword() {
        return password;
    }
}

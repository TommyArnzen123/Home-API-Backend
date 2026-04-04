package com.arnzen.home_api_backend.model.login;

public class LoginResponse {

    private int userId;
    private String username;
    private String firstName;
    private String jwtToken;

    public LoginResponse(int userId, String username, String firstName, String jwtToken) {
        this.userId = userId;
        this.username = username;
        this.firstName = firstName;
        this.jwtToken = jwtToken;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getJwtToken() {
        return jwtToken;
    }

    public void setJwtToken(String jwtToken) {
        this.jwtToken = jwtToken;
    }
}

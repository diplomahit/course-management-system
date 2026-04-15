package com.course.dto;

import java.util.Map;

public class UserDetailResponse {
    private Long userId;
    private String username;
    private String nickname;
    private String userType;
    private String role;
    private String token;
    private Map<String, Object> profile;
    
    // Getters and Setters
    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getNickname() { return nickname; }
    public void setNickname(String nickname) { this.nickname = nickname; }
    public String getUserType() { return userType; }
    public void setUserType(String userType) { this.userType = userType; }
    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }
    public Map<String, Object> getProfile() { return profile; }
    public void setProfile(Map<String, Object> profile) { this.profile = profile; }
}

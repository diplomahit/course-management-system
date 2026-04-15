package com.course.service;

import com.course.dto.ApiResponse;
import com.course.entity.User;
import com.course.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class UserService {
    
    @Autowired
    private UserRepository userRepository;
    
    public ApiResponse<Map<String, Object>> login(String username, String password, String ip) {
        // Find user
        User user = userRepository.findByUsername(username);
        
        if (user == null) {
            return ApiResponse.error(401, "User not found");
        }
        
        // Check status
        if (user.getStatus() == 0) {
            return ApiResponse.error(403, "Account is disabled");
        }
        
        if (user.getStatus() == 2) {
            return ApiResponse.error(403, "Account is locked");
        }
        
        // Check password (simple comparison, should use BCrypt in production)
        if (!user.getPassword().equals(password)) {
            userRepository.incrementFailCount(username);
            return ApiResponse.error(401, "Invalid password");
        }
        
        // Update login info
        userRepository.updateLoginInfo(user.getId(), ip);
        
        // Generate token
        String token = UUID.randomUUID().toString().replace("-", "");
        
        // Return user info
        Map<String, Object> data = new HashMap<>();
        data.put("token", token);
        data.put("userId", user.getId());
        data.put("username", user.getUsername());
        data.put("nickname", user.getNickname());
        data.put("role", user.getRole());
        
        return ApiResponse.success(data);
    }
}
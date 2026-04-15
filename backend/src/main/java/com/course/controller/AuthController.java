package com.course.controller;

import com.course.dto.ApiResponse;
import com.course.dto.LoginRequest;
import com.course.dto.UserDetailResponse;
import com.course.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin
public class AuthController {
    
    @Autowired
    private AuthService authService;
    
    @PostMapping("/login")
    public ApiResponse<UserDetailResponse> login(@RequestBody LoginRequest request) {
        UserDetailResponse user = authService.login(request.getUsername(), request.getPassword());
        if (user == null) {
            return ApiResponse.error(401, "用户名或密码错误");
        }
        return ApiResponse.success(user);
    }
    
    @GetMapping("/userinfo")
    public ApiResponse<UserDetailResponse> getUserInfo(@RequestParam String token) {
        UserDetailResponse user = authService.getUserByToken(token);
        if (user == null) {
            return ApiResponse.error(401, "登录已过期，请重新登录");
        }
        return ApiResponse.success(user);
    }
    
    @PostMapping("/logout")
    public ApiResponse<Void> logout(@RequestParam String token) {
        authService.logout(token);
        return ApiResponse.success(null);
    }
}


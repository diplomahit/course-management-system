package com.course.controller;

import com.course.dto.ApiResponse;
import com.course.entity.SysUser;
import com.course.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/user")
@CrossOrigin
public class UserController {
    @Autowired
    private SysUserService sysUserService;
    
    @GetMapping("/list")
    public ApiResponse<List<SysUser>> list(@RequestParam(required = false) String keyword) {
        List<SysUser> users;
        if (keyword != null && !keyword.isEmpty()) {
            users = sysUserService.search(keyword);
        } else {
            users = sysUserService.findAll();
        }
        return ApiResponse.success(users);
    }
    
    @GetMapping("/{id}")
    public ApiResponse<SysUser> getById(@PathVariable Long id) {
        return sysUserService.findById(id)
                .map(ApiResponse::success)
                .orElse(ApiResponse.error(404, "用户不存在"));
    }
    
    @PostMapping("/save")
    public ApiResponse<SysUser> save(@RequestBody SysUser user) {
        if (user.getId() == null && sysUserService.existsByUsername(user.getUsername())) {
            return ApiResponse.error(400, "用户名已存在");
        }
        return ApiResponse.success(sysUserService.save(user));
    }
    
    @DeleteMapping("/{id}")
    public ApiResponse<Void> delete(@PathVariable Long id) {
        sysUserService.deleteById(id);
        return ApiResponse.success(null);
    }
}

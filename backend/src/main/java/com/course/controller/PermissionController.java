package com.course.controller;

import com.course.dto.ApiResponse;
import com.course.entity.Permission;
import com.course.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/permission")
@CrossOrigin
public class PermissionController {
    @Autowired
    private PermissionService permissionService;
    
    @GetMapping("/list")
    public ApiResponse<List<Permission>> list(@RequestParam(required = false) String keyword,
                                               @RequestParam(required = false) String module) {
        List<Permission> permissions;
        if (module != null && !module.isEmpty()) {
            permissions = permissionService.findByModule(module);
        } else if (keyword != null && !keyword.isEmpty()) {
            permissions = permissionService.search(keyword);
        } else {
            permissions = permissionService.findAll();
        }
        return ApiResponse.success(permissions);
    }
    
    @GetMapping("/{id}")
    public ApiResponse<Permission> getById(@PathVariable Long id) {
        return permissionService.findById(id)
                .map(ApiResponse::success)
                .orElse(ApiResponse.error(404, "权限不存在"));
    }
    
    @GetMapping("/role/{roleId}")
    public ApiResponse<List<Permission>> getByRoleId(@PathVariable Long roleId) {
        return ApiResponse.success(permissionService.findByRoleId(roleId));
    }
    
    @GetMapping("/user/{userId}")
    public ApiResponse<List<Permission>> getByUserId(@PathVariable Long userId) {
        return ApiResponse.success(permissionService.findByUserId(userId));
    }
    
    @PostMapping("/save")
    public ApiResponse<Permission> save(@RequestBody Permission permission) {
        return ApiResponse.success(permissionService.save(permission));
    }
    
    @DeleteMapping("/{id}")
    public ApiResponse<Void> delete(@PathVariable Long id) {
        permissionService.deleteById(id);
        return ApiResponse.success(null);
    }
}

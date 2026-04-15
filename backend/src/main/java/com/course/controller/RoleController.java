package com.course.controller;

import com.course.dto.ApiResponse;
import com.course.entity.Role;
import com.course.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/role")
@CrossOrigin
public class RoleController {
    @Autowired
    private RoleService roleService;
    
    @GetMapping("/list")
    public ApiResponse<List<Role>> list(@RequestParam(required = false) String keyword) {
        List<Role> roles;
        if (keyword != null && !keyword.isEmpty()) {
            roles = roleService.search(keyword);
        } else {
            roles = roleService.findAll();
        }
        return ApiResponse.success(roles);
    }
    
    @GetMapping("/{id}")
    public ApiResponse<Role> getById(@PathVariable Long id) {
        return roleService.findById(id)
                .map(ApiResponse::success)
                .orElse(ApiResponse.error(404, "角色不存在"));
    }
    
    @PostMapping("/save")
    public ApiResponse<Role> save(@RequestBody Role role) {
        if (role.getId() == null && roleService.existsByRoleCode(role.getRoleCode())) {
            return ApiResponse.error(400, "角色代码已存在");
        }
        return ApiResponse.success(roleService.save(role));
    }
    
    @DeleteMapping("/{id}")
    public ApiResponse<Void> delete(@PathVariable Long id) {
        roleService.deleteById(id);
        return ApiResponse.success(null);
    }
}

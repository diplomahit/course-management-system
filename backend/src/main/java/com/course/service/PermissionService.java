package com.course.service;

import com.course.entity.Permission;
import com.course.repository.PermissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class PermissionService {
    @Autowired
    private PermissionRepository permissionRepository;
    
    public List<Permission> findAll() {
        return permissionRepository.findAll();
    }
    
    public Optional<Permission> findById(Long id) {
        return permissionRepository.findById(id);
    }
    
    public Permission save(Permission permission) {
        return permissionRepository.save(permission);
    }
    
    public void deleteById(Long id) {
        permissionRepository.deleteById(id);
    }
    
    public List<Permission> search(String keyword) {
        return permissionRepository.search(keyword);
    }
    
    public List<Permission> findByModule(String module) {
        return permissionRepository.findByModule(module);
    }
    
    public List<Permission> findByRoleId(Long roleId) {
        return permissionRepository.findByRoleId(roleId);
    }
    
    public List<Permission> findByUserId(Long userId) {
        return permissionRepository.findByUserId(userId);
    }
}

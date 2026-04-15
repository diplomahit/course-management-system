package com.course.service;

import com.course.entity.SysUser;
import com.course.repository.SysUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class SysUserService {
    @Autowired
    private SysUserRepository sysUserRepository;
    
    public List<SysUser> findAll() {
        return sysUserRepository.findAll();
    }
    
    public Optional<SysUser> findById(Long id) {
        return sysUserRepository.findById(id);
    }
    
    public SysUser save(SysUser user) {
        return sysUserRepository.save(user);
    }
    
    public void deleteById(Long id) {
        sysUserRepository.deleteById(id);
    }
    
    public List<SysUser> search(String keyword) {
        return sysUserRepository.search(keyword);
    }
    
    public Optional<SysUser> findByUsername(String username) {
        return sysUserRepository.findByUsername(username);
    }
    
    public boolean existsByUsername(String username) {
        return sysUserRepository.existsByUsername(username);
    }
}

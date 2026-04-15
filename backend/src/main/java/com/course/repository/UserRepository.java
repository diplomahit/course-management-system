package com.course.repository;

import com.course.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserRepository {
    
    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    public User findByUsername(String username) {
        String sql = "SELECT id, username, password, nickname, email, phone, status, role FROM sys_user WHERE username = ? AND deleted = 0";
        List<User> users = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(User.class), username);
        return users.isEmpty() ? null : users.get(0);
    }
    
    public int updateLoginInfo(Long id, String ip) {
        String sql = "UPDATE sys_user SET last_login_time = NOW(), last_login_ip = ?, login_fail_count = 0 WHERE id = ?";
        return jdbcTemplate.update(sql, ip, id);
    }
    
    public int incrementFailCount(String username) {
        String sql = "UPDATE sys_user SET login_fail_count = login_fail_count + 1 WHERE username = ?";
        return jdbcTemplate.update(sql, username);
    }
}
package com.course.repository;

import com.course.entity.SysUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.Optional;
import java.util.List;

public interface SysUserRepository extends JpaRepository<SysUser, Long> {
    Optional<SysUser> findByUsername(String username);
    boolean existsByUsername(String username);
    
    @Query("SELECT u FROM SysUser u WHERE u.username LIKE %?1% OR u.nickname LIKE %?1%")
    List<SysUser> search(String keyword);
    
    @Query("SELECT u FROM SysUser u WHERE u.username = ?1 AND u.status = 'active'")
    Optional<SysUser> findActiveUser(String username);
}

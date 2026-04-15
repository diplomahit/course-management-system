package com.course.repository;

import com.course.entity.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;
import java.util.Optional;

public interface PermissionRepository extends JpaRepository<Permission, Long> {
    Optional<Permission> findByPermissionCode(String permissionCode);
    List<Permission> findByModule(String module);
    
    @Query("SELECT p FROM Permission p WHERE p.permissionName LIKE %?1% OR p.permissionCode LIKE %?1%")
    List<Permission> search(String keyword);
    
    @Query(value = "SELECT p.* FROM permission p JOIN role_permission rp ON p.id = rp.permission_id JOIN user_role ur ON rp.role_id = ur.role_id WHERE ur.user_id = ?1", nativeQuery = true)
    List<Permission> findByUserId(Long userId);
    
    @Query(value = "SELECT p.* FROM permission p JOIN role_permission rp ON p.id = rp.permission_id WHERE rp.role_id = ?1", nativeQuery = true)
    List<Permission> findByRoleId(Long roleId);
}

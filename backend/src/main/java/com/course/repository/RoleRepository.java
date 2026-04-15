package com.course.repository;

import com.course.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;
import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByRoleCode(String roleCode);
    List<Role> findByStatus(String status);
    boolean existsByRoleCode(String roleCode);
    
    @Query("SELECT r FROM Role r WHERE r.roleName LIKE %?1% OR r.roleCode LIKE %?1%")
    List<Role> search(String keyword);
}

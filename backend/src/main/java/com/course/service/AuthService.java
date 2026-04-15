package com.course.service;

import com.course.dto.UserDetailResponse;
import com.course.entity.Student;
import com.course.entity.Teacher;
import com.course.entity.SysUser;
import com.course.repository.StudentRepository;
import com.course.repository.TeacherRepository;
import com.course.repository.SysUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
public class AuthService {
    @Autowired
    private SysUserRepository sysUserRepository;
    
    @Autowired
    private StudentRepository studentRepository;
    
    @Autowired
    private TeacherRepository teacherRepository;
    
    // 简单的 token 存储（生产环境应使用 Redis）
    private Map<String, Long> tokenStore = new HashMap<>();
    
    public UserDetailResponse login(String username, String password) {
        Optional<SysUser> userOpt = sysUserRepository.findActiveUser(username);
        
        if (!userOpt.isPresent()) {
            return null;
        }
        
        SysUser user = userOpt.get();
        
        // 验证密码 (MD5)
        String md5Password = md5(password);
        if (!user.getPassword().equalsIgnoreCase(md5Password)) {
            return null;
        }
        
        // 生成 token
        String token = UUID.randomUUID().toString().replace("-", "");
        tokenStore.put(token, user.getId());
        
        // 构建响应
        UserDetailResponse response = new UserDetailResponse();
        response.setUserId(user.getId());
        response.setUsername(user.getUsername());
        response.setNickname(user.getNickname());
        response.setUserType(user.getUserType());
        response.setRole(user.getRole());
        response.setToken(token);
        
        // 根据用户类型获取详细信息
        Map<String, Object> profile = new HashMap<>();
        
        if ("student".equals(user.getUserType()) && user.getStudentId() != null) {
            Optional<Student> studentOpt = studentRepository.findById(user.getStudentId());
            if (studentOpt.isPresent()) {
                Student student = studentOpt.get();
                profile.put("studentNo", student.getStudentNo());
                profile.put("name", student.getName());
                profile.put("gender", student.getGender());
                profile.put("birthDate", student.getBirthDate());
                profile.put("phone", student.getPhone());
                profile.put("email", student.getEmail());
                profile.put("className", student.getClassName());
            }
        } else if ("teacher".equals(user.getUserType()) && user.getTeacherId() != null) {
            Optional<Teacher> teacherOpt = teacherRepository.findById(user.getTeacherId());
            if (teacherOpt.isPresent()) {
                Teacher teacher = teacherOpt.get();
                profile.put("teacherNo", teacher.getTeacherNo());
                profile.put("name", teacher.getName());
                profile.put("gender", teacher.getGender());
                profile.put("birthDate", teacher.getBirthDate());
                profile.put("phone", teacher.getPhone());
                profile.put("email", teacher.getEmail());
                profile.put("department", teacher.getDepartment());
                profile.put("title", teacher.getTitle());
            }
        } else if ("admin".equals(user.getUserType())) {
            profile.put("name", user.getNickname());
        }
        
        response.setProfile(profile);
        return response;
    }
    
    public UserDetailResponse getUserByToken(String token) {
        Long userId = tokenStore.get(token);
        if (userId == null) {
            return null;
        }
        
        Optional<SysUser> userOpt = sysUserRepository.findById(userId);
        if (!userOpt.isPresent()) {
            return null;
        }
        
        SysUser user = userOpt.get();
        
        UserDetailResponse response = new UserDetailResponse();
        response.setUserId(user.getId());
        response.setUsername(user.getUsername());
        response.setNickname(user.getNickname());
        response.setUserType(user.getUserType());
        response.setRole(user.getRole());
        response.setToken(token);
        
        Map<String, Object> profile = new HashMap<>();
        
        if ("student".equals(user.getUserType()) && user.getStudentId() != null) {
            Optional<Student> studentOpt = studentRepository.findById(user.getStudentId());
            if (studentOpt.isPresent()) {
                Student student = studentOpt.get();
                profile.put("studentNo", student.getStudentNo());
                profile.put("name", student.getName());
                profile.put("gender", student.getGender());
                profile.put("birthDate", student.getBirthDate());
                profile.put("phone", student.getPhone());
                profile.put("email", student.getEmail());
                profile.put("className", student.getClassName());
            }
        } else if ("teacher".equals(user.getUserType()) && user.getTeacherId() != null) {
            Optional<Teacher> teacherOpt = teacherRepository.findById(user.getTeacherId());
            if (teacherOpt.isPresent()) {
                Teacher teacher = teacherOpt.get();
                profile.put("teacherNo", teacher.getTeacherNo());
                profile.put("name", teacher.getName());
                profile.put("gender", teacher.getGender());
                profile.put("birthDate", teacher.getBirthDate());
                profile.put("phone", teacher.getPhone());
                profile.put("email", teacher.getEmail());
                profile.put("department", teacher.getDepartment());
                profile.put("title", teacher.getTitle());
            }
        } else if ("admin".equals(user.getUserType())) {
            profile.put("name", user.getNickname());
        }
        
        response.setProfile(profile);
        return response;
    }
    
    public void logout(String token) {
        tokenStore.remove(token);
    }
    
    private String md5(String input) {
        try {
            java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
            byte[] digest = md.digest(input.getBytes("UTF-8"));
            StringBuilder sb = new StringBuilder();
            for (byte b : digest) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (Exception e) {
            return "";
        }
    }
}

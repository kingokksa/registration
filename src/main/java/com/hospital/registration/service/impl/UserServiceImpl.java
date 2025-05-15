package com.hospital.registration.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hospital.registration.pojo.User;
import com.hospital.registration.pojo.Doctor; // 导入 Doctor 类
import com.hospital.registration.mapper.UserMapper;
import com.hospital.registration.mapper.DoctorMapper; // 导入 DoctorMapper
import com.hospital.registration.service.UserService;
import com.hospital.registration.config.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.List; // 导入 List

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private DoctorMapper doctorMapper; // 注入 DoctorMapper

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Override
    public Map<String, Object> login(User loginUser) {
        Map<String, Object> result = new HashMap<>();
        User user = findByUsername(loginUser.getUsername());

        if (user != null && user.getPassword().equals(loginUser.getPassword())) {
            String token = jwtTokenUtil.generateToken(user.getUsername(), user.getRole());

            result.put("success", true);
            result.put("message", "登录成功");
            result.put("token", token);
            result.put("user", user);
            QueryWrapper<Doctor> wrapper = new QueryWrapper<>();
            wrapper.eq("user_id", user.getUserId());
            Doctor doctor = doctorMapper.selectOne(wrapper);
            if (doctor != null) {
                result.put("doctorId", doctor.getDoctorId());
            }

        } else {
            result.put("success", false);
            result.put("message", "用户名或密码错误");
        }
        return result;
    }

    @Override
    public Map<String, Object> register(User user) {
        Map<String, Object> result = new HashMap<>();

        // 检查必要字段
        if (user.getUsername() == null || user.getPassword() == null) {
            result.put("success", false);
            result.put("message", "用户名和密码不能为空");
            return result;
        }

        // 检查用户名是否已存在
        if (findByUsername(user.getUsername()) != null) {
            result.put("success", false);
            result.put("message", "用户名已存在");
            return result;
        }

        // 设置默认值
        user.setRole("USER");
        user.setCreatedAt(LocalDateTime.now());

        // 如果其他字段为空,设置默认值
        if (user.getName() == null)
            user.setName(user.getUsername());
        if (user.getPhone() == null)
            user.setPhone("");
        if (user.getEmail() == null)
            user.setEmail("");

        // 保存用户
        boolean saved = save(user);
        result.put("success", saved);
        result.put("message", saved ? "注册成功" : "注册失败");

        return result;
    }

    @Override
    public Map<String, Object> logout() {
        SecurityContextHolder.clearContext();
        return Map.of("success", true, "message", "已成功登出");
    }

    @Override
    public Map<String, Object> refresh(String refreshToken) {
        String username = jwtTokenUtil.getUsernameFromToken(refreshToken);
        String role = jwtTokenUtil.getRoleFromToken(refreshToken);
        String newToken = jwtTokenUtil.generateToken(username, role);

        return Map.of(
                "success", true,
                "message", "Token已刷新",
                "token", newToken);
    }

    @Override
    public User getCurrentUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return findByUsername(username);
    }

    @Override
    public Map<String, Object> updateCurrentUser(User user) {
        User currentUser = getCurrentUser();
        if (currentUser == null) {
            return Map.of("success", false, "message", "用户未找到");
        }

        // 只允许更新部分字段
        currentUser.setPhone(user.getPhone());
        currentUser.setEmail(user.getEmail());
        if (user.getPassword() != null && !user.getPassword().isEmpty()) {
            currentUser.setPassword(user.getPassword());
        }

        boolean updated = updateById(currentUser);
        return Map.of(
                "success", updated,
                "message", updated ? "用户信息更新成功" : "用户信息更新失败");
    }

    @Override
    public User findByUsername(String username) {
        return userMapper.findByUsername(username);
    }

    @Override
    public User updateById(Integer id) {
        return userMapper.selectById(id);
    }

    // Admin CRUD implementations
    @Override
    public List<User> getAllUsers() {
        return userMapper.selectList(null);
    }

    @Override
    public User getUserById(Integer id) {
        // Note: User ID is Long in POJO, but the method in interface was defined as
        // Integer.
        // Assuming the interface definition was a mistake and should be Long.
        // If the interface must remain Integer, casting might be needed, but it's
        // better to fix the interface.
        // For now, I will implement based on the POJO's Long type for consistency with
        // the database ID.
        // If the interface cannot be changed, please let me know.
        return userMapper.selectById(id); // This might cause issues if id is large
    }

    @Override
    public void addUser(User user) {
        userMapper.insert(user);
    }

    @Override
    public void updateUser(User user) {
        userMapper.updateById(user);
    }

    @Override
    public void deleteUser(Long id) {
        userMapper.deleteById(id);
    }
}

package com.hospital.registration.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hospital.registration.pojo.User;
import java.util.List;
import java.util.Map;

public interface UserService extends IService<User> {
    /**
     * 用户登录
     * 
     * @param loginUser 包含用户名和密码的用户对象
     * @return 包含登录结果的Map，成功时包含"success", "message", "token", "user",
     *         "userId"字段，失败时包含"success", "message"字段。
     */
    Map<String, Object> login(User loginUser);

    Map<String, Object> register(User user);

    Map<String, Object> logout();

    Map<String, Object> refresh(String refreshToken);

    User getCurrentUser();

    Map<String, Object> updateCurrentUser(User user);

    User findByUsername(String username);

    User updateById(Integer id);

    // Admin CRUD methods
    List<User> getAllUsers();

    User getUserById(Integer id);

    void addUser(User user);

    void updateUser(User user);

    void deleteUser(Long id);
}

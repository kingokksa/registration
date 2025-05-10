package com.hospital.registration.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hospital.registration.pojo.User;
import java.util.Map;

public interface UserService extends IService<User> {
    Map<String, Object> login(User loginUser);

    Map<String, Object> register(User user);

    Map<String, Object> logout();

    Map<String, Object> refresh(String refreshToken);

    User getCurrentUser();

    Map<String, Object> updateCurrentUser(User user);

    User findByUsername(String username);

    User updateById(Integer id);
}

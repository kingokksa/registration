package com.hospital.registration.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hospital.registration.entity.User;

public interface UserService extends IService<User> {
    User findByUsername(String username);
}

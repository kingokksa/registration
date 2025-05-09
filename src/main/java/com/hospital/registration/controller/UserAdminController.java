package com.hospital.registration.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/users")
public class UserAdminController {

    @GetMapping
    public Object listUsers() {
        // 获取用户列表
        return null;
    }

    @PutMapping("/{id}")
    public Object updateUser(@PathVariable Long id, @RequestBody Object user) {
        // 更新用户信息
        return null;
    }

    @DeleteMapping("/{id}")
    public Object deleteUser(@PathVariable Long id) {
        // 删除用户
        return null;
    }
}

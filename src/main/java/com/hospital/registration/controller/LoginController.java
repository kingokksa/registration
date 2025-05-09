package com.hospital.registration.controller;

import com.hospital.registration.pojo.User;
import com.hospital.registration.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

@CrossOrigin
@RestController
@RequestMapping("/auth")
public class LoginController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public Map<String, Object> login(@RequestBody User loginUser, HttpSession session) {
        Map<String, Object> result = new HashMap<>();
        User user = userService.findByUsername(loginUser.getUsername());

        if (user != null && user.getPassword().equals(loginUser.getPassword())) {
            session.setAttribute("user", user);
            result.put("success", true);
            result.put("code", 200);
            result.put("message", "登录成功");
            result.put("user", user);
        } else {
            result.put("success", false);
            result.put("message", "用户名或密码无效");
        }
        return result;
    }

    @PostMapping("/register")
    public Map<String, Object> register(@RequestBody User user) {
        Map<String, Object> result = new HashMap<>();
        User exist = userService.findByUsername(user.getUsername());
        if (exist != null) {
            result.put("success", false);
            result.put("message", "用户名已存在");
            return result;
        }
        boolean saved = userService.save(user);
        result.put("success", saved);
        result.put("message", saved ? "注册成功" : "注册失败");
        return result;
    }

    @PostMapping("/logout")
    public Map<String, Object> logout(HttpSession session) {
        session.invalidate();
        Map<String, Object> result = new HashMap<>();
        result.put("success", true);
        result.put("message", "已登出");
        return result;
    }

    @GetMapping("/user")
    public Map<String, Object> getCurrentUser(HttpSession session) {
        Map<String, Object> result = new HashMap<>();
        User user = (User) session.getAttribute("user");
        if (user != null) {
            result.put("success", true);
            result.put("user", user);
        } else {
            result.put("success", false);
            result.put("message", "未登录");
        }
        return result;
    }

    @PutMapping("/user")
    public Map<String, Object> updateCurrentUser(@RequestBody User user) {
        if (userService.updateById(user)) {
            return Map.of("success", true, "message", "更新成功");
        } else {
            return Map.of("success", false, "message", "更新失败");
        }
    }

    @PostMapping("/refresh")
    public Map<String, Object> refreshToken(@RequestBody Map<String, String> body) {
        Map<String, Object> result = new HashMap<>();
        result.put("success", true);
        result.put("message", "Token已刷新（演示）");
        return result;
    }
}

package com.practice1.projava.studylist;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.UUID;

@Controller
public class UserController {

    private final UserDao userDao;
    private final PasswordEncoder passwordEncoder;

    public UserController(UserDao userDao, PasswordEncoder passwordEncoder) {
        this.userDao = userDao;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/register")
    public String showRegister() {
        return "register";
    }

    @PostMapping("/register")
    public String register(
            @RequestParam("username") String username,
            @RequestParam("password") String password) {
        String id = UUID.randomUUID().toString().substring(0, 8);
        String encodedPassword = passwordEncoder.encode(password);
        AppUser user = new AppUser(id, username, encodedPassword);
        userDao.add(user);
        return "redirect:/login";
    }
}
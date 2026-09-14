package com.practice1.projava.studylist;

import jakarta.validation.Valid;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

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
    public String showRegister(Model model) {
        model.addAttribute("registerForm", new RegisterForm());
        return "register";
    }

    @PostMapping("/register")
    public String register(
            @Valid @ModelAttribute("registerForm") RegisterForm form,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "register";
        }
        try {
            userDao.findByUsername(form.getUsername());

            bindingResult.rejectValue(
                    "username",
                    "duplicate",
                    "このユーザー名はすでに使用されています"
            );
            return "register";

        } catch (EmptyResultDataAccessException e) {

            String id = UUID.randomUUID().toString().substring(0, 8);

            String encodedPassword =
                    passwordEncoder.encode(form.getPassword());

            AppUser user = new AppUser(
                    id,
                    form.getUsername(),
                    encodedPassword
            );
            userDao.add(user);
            return "redirect:/login";
        }
    }
}
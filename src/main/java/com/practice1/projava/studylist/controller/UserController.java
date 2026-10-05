package com.practice1.projava.studylist.controller;

import com.practice1.projava.studylist.dao.UserDao;
import com.practice1.projava.studylist.form.RegisterForm;
import com.practice1.projava.studylist.model.AppUser;
import jakarta.validation.Valid;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.UUID;

/**
 * ユーザー登録画面の表示と、新規ユーザーの登録処理を担当するコントローラー
 */
@Controller
public class UserController {

    private final UserDao userDao;
    private final PasswordEncoder passwordEncoder;

    public UserController(UserDao userDao, PasswordEncoder passwordEncoder) {
        this.userDao = userDao;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * ユーザー登録画面を表示する
     * @param model　画面に表示するデータを渡すためのModel
     * @return　ユーザー登録画面名
     */
    @GetMapping("/register")
    public String showRegister(Model model) {
        model.addAttribute("registerForm", new RegisterForm());

        return "register";
    }

    /**
     * 入力されたユーザー情報を検証し、新しいユーザーを登録する
     * @param form　ユーザー登録の入力内容
     * @param bindingResult　バリデーション結果
     * @return　登録成功時はログイン画面へリダイレクトし、入力エラーやユーザ名重複時は登録画面を再表示する
     */
    @PostMapping("/register")
    public String register(@Valid @ModelAttribute("registerForm") RegisterForm form, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "register";
        }

        try {
            userDao.findByUsername(form.getUsername());

            bindingResult.rejectValue("username", "duplicate", "このユーザー名はすでに使用されています");
            return "register";
        } catch (EmptyResultDataAccessException e) {
            String id = UUID.randomUUID().toString().substring(0, 8);
            String encodedPassword = passwordEncoder.encode(form.getPassword());
            AppUser user = new AppUser(id, form.getUsername(), encodedPassword);
            userDao.add(user);

            return "redirect:/login";
        }
    }
}
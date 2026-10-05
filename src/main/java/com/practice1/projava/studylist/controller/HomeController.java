package com.practice1.projava.studylist.controller;

import com.practice1.projava.studylist.dao.StudyListDao;
import com.practice1.projava.studylist.form.StudyForm;
import com.practice1.projava.studylist.model.StudyItem;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.UUID;

/**
 * 学習記録の一覧表示・登録・更新・削除など、
 * 学習管理画面に関するリクエストを処理するコントローラー
 */
@Controller
public class HomeController {
    private final StudyListDao dao;

    @Autowired
    HomeController(StudyListDao dao) {
        this.dao = dao;
    }

    /**
     *ログインユーザーの学習記録を取得し、学習一覧画面を表示する
     *
     * @param model　画面に表示するデータを渡すためのModel
     * @param authentication　ログインユーザーの認証情報
     * @return　表示する画面名
     */
    @GetMapping("/list")
    public String showStudyList(Model model, Authentication authentication) {
        String username = authentication.getName();
        List<StudyItem> studyItems = dao.findByUsername(username);

        model.addAttribute("studyList", studyItems);
        model.addAttribute("username", username);
        model.addAttribute("studyForm", new StudyForm());
        model.addAttribute("updateForm", new StudyForm());

        return "home";
    }

    /**
     * 入力された学習記録を検証し、ログインユーザーの学習記録として登録する
     * @param form　学習記録の入力内容
     * @param bindingResult　バリデーション結果
     * @param authentication　ログインユーザーの認証情報
     * @param model　画面に表示するデータを渡すためのModel
     * @return　登録成功時は学習一覧画面へリダイレクトし、入力エラー時は入力画面を再表示する
     */
    @PostMapping("/add")
    String addRecord(@Valid @ModelAttribute("studyForm") StudyForm form, BindingResult bindingResult,
                     Authentication authentication, Model model) {

        String username = authentication.getName();

        if (bindingResult.hasErrors()) {
            List<StudyItem> studyItems = dao.findByUsername(username);
            model.addAttribute("studyList", studyItems);
            model.addAttribute("username", username);
            return "home";
        }

        String id = UUID.randomUUID().toString().substring(0, 8);
        StudyItem item = new StudyItem(id, form.getDate(), form.getContent(), form.getTime(), username);
        dao.add(item);

        return "redirect:/list";
    }

    /**
     * 指定された学習記録をログインユーザーの記録から削除する
     *
     * @param id　削除する学習記録のID
     * @param authentication　ログインユーザーの認証
     * @return　学習一覧画面へのリダイレクト
     */
    @PostMapping("/delete")
    String deleteItem(@RequestParam("id") String id, Authentication authentication) {

        String username = authentication.getName();
        dao.delete(id, username);

        return "redirect:/list";
    }

    /**
     * 入力された学習記録を検証し、ログインユーザーの学習記録を更新する
     *
     * @param form　更新する学習記録の入力内容
     * @param bindingResult　バリデーション結果
     * @param authentication　ログインユーザーの認証情報
     * @param model　画面に表示するデータを渡すためのModel
     * @return　更新成功時は学習一覧画面へリダイレクトし、入力エラー時は入力画面を再表示する
     */
    @PostMapping("/update")
    String updateItem(@Valid @ModelAttribute("updateForm") StudyForm form, BindingResult bindingResult,
                      Authentication authentication, Model model) {

        String username = authentication.getName();

        if (bindingResult.hasErrors()) {
            List<StudyItem> studyItems = dao.findByUsername(username);
            model.addAttribute("studyList", studyItems);
            model.addAttribute("username", username);
            model.addAttribute("studyForm", new StudyForm());
            model.addAttribute("updateError", true);
            return "home";
        }

        StudyItem studyItem = new StudyItem(form.getId(), form.getDate(), form.getContent(), form.getTime(), username);
        dao.update(studyItem);

        return "redirect:/list";
    }

    /**
     * ログイン画面を表示する
     * @return　ログイン画面名
     */
    @GetMapping("/login")
    public String showLogin() {
        return "login";
    }
}
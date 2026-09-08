package com.practice1.projava.studylist;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.security.core.Authentication;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Controller
public class HomeController {
    private final StudyListDao dao;

    @Autowired
    HomeController(StudyListDao dao) {
        this.dao = dao;
    }
    record StudyItem(String id, LocalDate date, String content, Double time, String username) {
        public String formattedTime() {
            int totalMinutes = (int) Math.round(time * 60);
            int hours = totalMinutes / 60;
            int minutes = totalMinutes % 60;

            if (hours > 0 && minutes > 0) {
                return hours + "時間" + minutes + "分";
            } else if (hours > 0) {
                return hours + "時間";
            } else {
                return minutes + "分";
            }
        }
    }
    private List<StudyItem> studyItems = new ArrayList<>();

    @GetMapping ("/list")
public String showStudyList(Model model, Authentication authentication) {
        String username = authentication.getName();
        List<StudyItem> studyItems = dao.findByUsername(username);
    model.addAttribute("studyList", studyItems);
    model.addAttribute("username", username);
    return "home";
    }

@PostMapping("/add")
    String addRecord(@RequestParam("date") String date,
    @RequestParam("content") String content,
    @RequestParam("time") String time,
        Authentication authentication) {
    String username = authentication .getName();

        String id = UUID.randomUUID().toString().substring(0, 8);
    LocalDate parsedDate = LocalDate.parse(date);
    Double parsedTime = Double.parseDouble(time);
    StudyItem item = new StudyItem(id, parsedDate, content, parsedTime, username);
        dao.add(item);

        return "redirect:/list";
}
@PostMapping("/delete")
    String deleteItem(@RequestParam("id") String id,
        Authentication authentication) {

        String username = authentication.getName();

        dao.delete(id, username);
        return "redirect:/list";
}
@PostMapping("/update")
    String updateItem(@RequestParam("id") String id,
                      @RequestParam("date") LocalDate date,
                      @RequestParam("content") String content,
                      @RequestParam("time") Double time,
        Authentication authentication) {
        String username = authentication.getName();
        StudyItem studyItem = new StudyItem(id, date, content, time, username);
        dao.update(studyItem);
        return "redirect:/list";
}
@GetMapping("/login")
    public String showLogin() {
        return "login";
}
}
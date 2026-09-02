package com.practice1.projava.studylist;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
    record StudyItem(String id, LocalDate date, String content, Double time) {
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
public String showStudyList(Model model) {
        List<StudyItem> studyItems = dao.findAll();
    model.addAttribute("studyList", studyItems);
    return "home";
    }

@GetMapping("/add")
    String addRecord(@RequestParam("date") String date,
    @RequestParam("content") String content,
    @RequestParam("time") String time) {
        String id = UUID.randomUUID().toString().substring(0, 8);
    LocalDate parsedDate = LocalDate.parse(date);
    Double parsedTime = Double.parseDouble(time);
    StudyItem item = new StudyItem(id, parsedDate, content, parsedTime);
        dao.add(item);

        return "redirect:/list";
}
@GetMapping("/delete")
    String deleteItem(@RequestParam("id") String id) {
        dao.delete(id);
        return "redirect:/list";
}
@GetMapping("/update")
    String updateItem(@RequestParam("id") String id,
                      @RequestParam("date") LocalDate date,
                      @RequestParam("content") String content,
                      @RequestParam("time") Double time) {
        StudyItem studyItem = new StudyItem(id, date, content, time);
        dao.update(studyItem);
        return "redirect:/list";
}
}
package com.practice1.projava.studylist;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import org.springframework.format.annotation.DateTimeFormat;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public class StudyForm {

    @NotNull(message = "学習日を入力してください")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;

    @NotBlank(message = "学習内容を入力してください")
    @Size(max = 256, message = "学習内容は256文字以内で入力してください")
    private String content;

    @NotNull(message = "学習時間を入力してください")
    @PositiveOrZero(message = "学習時間は0以上で入力してください")
    private Double time;

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Double getTime() {
        return time;
    }

    public void setTime(Double time) {
        this.time = time;
    }
}
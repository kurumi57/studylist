package com.practice1.projava.studylist.form;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

/**
 * 学習記録の登録・更新画面で入力された値を受け取るフォームクラス
 */
public class StudyForm {

    private String id;

    @NotNull(message = "学習日を入力してください")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;

    @NotBlank(message = "学習内容を入力してください")
    @Size(max = 256, message = "学習内容は256文字以内で入力してください")
    private String content;

    @NotNull(message = "学習時間を入力してください")
    @Positive(message = "学習時間は0より大きい値で入力してください")
    private Double time;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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
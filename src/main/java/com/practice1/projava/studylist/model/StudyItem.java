package com.practice1.projava.studylist.model;

import java.time.LocalDate;

/**
 * 1件分の学習記録を表すモデル
 *
 * @param id　学習記録のID
 * @param date　学習日
 * @param content　学習内容
 * @param time　学習時間
 * @param username　学習記録を所有するユーザー名
 */
public record StudyItem(String id, LocalDate date, String content, Double time, String username) {

    /**
     * 学習時間を「◯時間◯分」の形式に変換する
     *
     * @return　表示用に整形した学習時間
     */
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
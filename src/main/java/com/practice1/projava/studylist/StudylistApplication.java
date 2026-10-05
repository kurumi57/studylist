package com.practice1.projava.studylist;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 学習管理アプリケーションを起動するためのメインクラス
 */
@SpringBootApplication
public class StudylistApplication {

    /**
     * Spring Bootアプリケーションを起動する
     *
     * @param args　コマンドライン引数
     */
    public static void main(String[] args) {
        SpringApplication.run(StudylistApplication.class, args);
    }
}
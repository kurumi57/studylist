package com.practice1.projava.studylist.model;

/**
 * アプリケーションのユーザー情報を表すモデル
 *
 * @param id　ユーザーID
 * @param username　ユーザー名
 * @param password　ハッシュ化されたパスワード
 */
public record AppUser(String id, String username, String password) {
}
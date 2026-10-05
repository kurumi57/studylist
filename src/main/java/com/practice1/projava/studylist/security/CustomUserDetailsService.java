package com.practice1.projava.studylist.security;

import com.practice1.projava.studylist.dao.UserDao;
import com.practice1.projava.studylist.model.AppUser;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Spring Securityの認証で使用するユーザー情報をデータベースから取得するサービスクラス
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserDao userDao;

    public CustomUserDetailsService(UserDao userDao) {
        this.userDao = userDao;
    }

    /**
     * 指定されたユーザー名からユーザー情報を取得し、Spring Securityの認証で使用するUserDetailsを生成する
     *
     * @param username ログイン時に入力されたユーザー名
     * @return　認証に使用するユーザー情報
     * @throws UsernameNotFoundException　ユーザーが見つからない場合
     */
    @Override
    public UserDetails loadUserByUsername(String username) {
        try {
            AppUser user = userDao.findByUsername(username);

            return User.withUsername(user.username()).password(user.password()).roles("USER").build();
        } catch (EmptyResultDataAccessException e) {
            throw new UsernameNotFoundException("ユーザーが見つかりません");
        }
    }
}
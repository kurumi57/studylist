package com.practice1.projava.studylist;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserDao userDao;

    public CustomUserDetailsService(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        try {
            AppUser user = userDao.findByUsername(username);

            return User.withUsername(user.username())
                    .password(user.password())
                    .roles("USER")
                    .build();
        } catch (EmptyResultDataAccessException e) {
            throw new UsernameNotFoundException("ユーザーが見つかりません");
        }
    }
}
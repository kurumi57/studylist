package com.practice1.projava.studylist.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * アプリケーションの認証・ログイン・ログアウトなど、
 * Spring Securityの設定を行うクラス
 */
@Configuration
public class SecurityConfig {

    /**
     * パスワードのハッシュ化に使用するPasswordEncoderを生成する
     *
     * @return　BCrypt方式のPasswordEncoder
     */
    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * アプリケーションのアクセス制御・ログイン・ログアウトに関する
     * Spring Securityの設定を行う
     *
     * @param http　Spring SecurityのHTTPセキュリティ設定
     * @return　設定済みのSecurityFilterChain
     * @throws Exception　セキュリティ設定の構築に失敗した場合
     */
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(auth -> auth.requestMatchers("/register").permitAll().anyRequest().authenticated());

        http.formLogin(form -> form.loginPage("/login").defaultSuccessUrl("/list", true).permitAll());

        http.logout(logout -> logout.logoutSuccessUrl("/login").permitAll());

        return http.build();
    }
}
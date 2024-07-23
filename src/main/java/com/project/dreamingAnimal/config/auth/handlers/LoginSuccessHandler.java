package com.project.dreamingAnimal.config.auth.handlers;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    public LoginSuccessHandler() {
        // 로그인 성공 시 이동할 URL 설정
        this.setDefaultTargetUrl("/");
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        // SecurityContextHolder를 통해 현재 인증된 사용자의 정보를 가져옴
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        super.onAuthenticationSuccess(request, response, authentication);
    }
}


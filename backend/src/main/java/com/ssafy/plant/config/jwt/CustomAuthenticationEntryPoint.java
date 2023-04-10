package com.ssafy.plant.config.jwt;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {

        String exception = (String) request.getAttribute(JwtProperties.HEADER_STRING);
        String errorCode;
        System.out.println("에러발생");
        System.out.println("에러발생");
        System.out.println("에러발생");
        System.out.println("에러발생");


        if (exception.equals("토큰이 만료되었습니다.")) {
            System.out.println("토큰만료");
            errorCode = "토큰이 만료되었습니다.";
            setResponse(response, errorCode);
        }

        if (exception.equals("유효하지 않은 토큰입니다.")) {
            System.out.println("유효x 토큰");
            errorCode = "유효하지 않은 토큰입니다.";
            setResponse(response, errorCode);
        }
    }
    // 스테이터스, 콘텐트 타입, 오류 메세지를 담아 응답해주는 메소드를 만들어 사용한다.
    private void setResponse(HttpServletResponse response, String errorCode) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().println(JwtProperties.HEADER_STRING + " : " + errorCode);
    }
}

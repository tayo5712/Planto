package com.ssafy.plant.config.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.ssafy.plant.config.auth.PrincipalDetails;
import com.ssafy.plant.domain.User;
import com.ssafy.plant.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {    // 한 요청당 반드시 한 번만 실행되는 필터

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // 요청 헤더의 Authorization 항목 값을 가져오기

        String jwtHeader = ((HttpServletRequest) request).getHeader(JwtProperties.HEADER_STRING);
        System.out.println("필터 발동");
        System.out.println(jwtHeader);
        // 만약 jwtHeader가 null 이거나 Bearer로 시작하지 않으면 필터 다시 태움
        if (jwtHeader == null || !jwtHeader.startsWith(JwtProperties.TOKEN_PREFIX)) {
            System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
            System.out.println("토큰이 없습니다.");
            System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
            filterChain.doFilter(request, response);
            return;
        }
        String token = jwtHeader.replace(JwtProperties.TOKEN_PREFIX, "");
        String socialId = null;

        try {
            // token을 비밀 키로 복호화함, 클레임에 넣어두었던 id값을 가져옴
            socialId = JWT.require(Algorithm.HMAC512(JwtProperties.SECRET))
                    .build()
                    .verify(token)
                    .getClaim("socialId")
                    .asString();
            System.out.println("???????????????????????");
            System.out.println(token);
            System.out.println(socialId);
            System.out.println("???????????????????????");

        } catch (TokenExpiredException e) {
            e.printStackTrace();
            request.setAttribute(JwtProperties.HEADER_STRING, "토큰이 만료되었습니다.");
        } catch (JWTVerificationException e) {
            e.printStackTrace();
            request.setAttribute(JwtProperties.HEADER_STRING, "유효하지 않은 토큰입니다.");
        }

        request.setAttribute("socialId", socialId);

        filterChain.doFilter(request, response);    // filterChain에 request, response 값 넘김
    }
}

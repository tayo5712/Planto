package com.ssafy.plant.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ssafy.plant.config.jwt.JwtProperties;
import com.ssafy.plant.config.oauth.OauthToken;
import com.ssafy.plant.domain.User;
import com.ssafy.plant.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.security.access.annotation.Secured;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/oauth/token") // 프론트에서 인가코드 받아오는 url
    public ResponseEntity<String> getLogin(@RequestParam("code") String code, HttpSession session) throws JsonProcessingException {
        System.out.println("인가코드받아왔어요");
        System.out.println(code);
//        // 넘어온 인가코드로 accesstoken
        OauthToken oauthToken = userService.getAccessToken(code);
        session.setAttribute("kakaoAccessToken", oauthToken.getAccess_token());
        // 발급 받은 accessToken으로 카카오 회원 정보 저장
        String jwtToken = userService.saveUser(oauthToken.getAccess_token());

        HttpHeaders headers = new HttpHeaders();
        headers.add(JwtProperties.HEADER_STRING, JwtProperties.TOKEN_PREFIX + jwtToken);
        System.out.println("=====================================");
        System.out.println(headers);
        System.out.println(jwtToken);
        System.out.println("=====================================");
        return ResponseEntity.status(HttpStatus.OK).headers(headers).body("success");
    }

    @GetMapping("/user/oauth/username")
    public ResponseEntity<User> getCurrentUser(HttpServletRequest request) {
        User user = userService.getUser(request);
        return ResponseEntity.status(HttpStatus.OK).body(user);
    }

    @RequestMapping("/user/oauth/logout")
    public ResponseEntity<String> logout(@RequestParam("code") String code) throws JsonProcessingException {
        OauthToken oauthToken = userService.getAccessToken(code);
        RestTemplate rt = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + oauthToken.getAccess_token());
        String url = "https://kapi.kakao.com/v1/user/logout";
        HttpEntity<MultiValueMap<String, String>> logoutRequest = new HttpEntity<>(headers);
        ResponseEntity<String> logoutResponse = rt.exchange(
                url,
                HttpMethod.POST,
                logoutRequest,
                String.class
        );
        return ResponseEntity.status(HttpStatus.OK).body(logoutResponse.getBody());
    }
}

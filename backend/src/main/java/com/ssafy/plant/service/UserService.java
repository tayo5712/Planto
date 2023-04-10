package com.ssafy.plant.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.plant.config.jwt.JwtProperties;
import com.ssafy.plant.config.oauth.OauthToken;
import com.ssafy.plant.config.oauth.Profile.KakaoProfile;
import com.ssafy.plant.domain.User;
import com.ssafy.plant.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Value("${java.oauth.kakao.clientId}")
    private String clientId;

    @Value("${java.oauth.kakao.redirectUri}")
    private String redirectId;

    public OauthToken getAccessToken(String code) throws JsonProcessingException {
        RestTemplate rt = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", clientId);
        params.add("redirect_uri", redirectId);
        params.add("code", code);

        // HttpHeader 와 HttpBody 정보를 하나의 객체에 담기
        HttpEntity<MultiValueMap<String, String>> TokenRequest =
                new HttpEntity<>(params, headers);

        ResponseEntity<String> accessTokenResponse = rt.exchange(
                "https://kauth.kakao.com/oauth/token",
                HttpMethod.POST,
                TokenRequest,
                String.class
        );
        ObjectMapper objectMapper = new ObjectMapper();
        OauthToken oauthToken = objectMapper.readValue(accessTokenResponse.getBody(), OauthToken.class);
        // .readValue(Json 데이터, 변환할 클래스) 메소드를 이용해 바디값 읽어오기
        System.out.println(oauthToken);
        return oauthToken;
    }

    @Transactional
    public String saveUser(String token) throws JsonProcessingException {
        KakaoProfile profile = searchProfile(token);

        String socialId = "kakao" + profile.getId();
        String name = profile.properties.getNickname();
        String profileImageUrl = profile.kakao_account.getProfile().getProfile_image_url();

        User user = userRepository.findBySocialId(socialId);  // db에 저장 되어 있는 유저인지 확인

        if (user == null) {
            user = User.builder()
                    .socialId(socialId)
                    .name(name)
                    .profileImageUrl(profileImageUrl)
                    .role("ROLE_USER")
                    .build();
            userRepository.save(user);
        }
        return createToken(user);
    }

    @Transactional
    public String createToken(User user) {
        System.out.println("토큰생성**************************");
        String jwtToken = JWT.create()
                .withSubject(user.getSocialId())
                .withExpiresAt(new Date(System.currentTimeMillis() + JwtProperties.EXPIRATION_TIME))
                .withClaim("socialId", user.getSocialId())
                .sign(Algorithm.HMAC512(JwtProperties.SECRET));
        return jwtToken;
    }

    public KakaoProfile searchProfile(String token) throws JsonProcessingException {
        RestTemplate rt = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + token);
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        HttpEntity<MultiValueMap<String, String>> profileRequest = new HttpEntity<>(headers);
        ResponseEntity<String> profileResponse = rt.exchange(
                "https://kapi.kakao.com/v2/user/me",
                HttpMethod.POST,
                profileRequest,
                String.class
        );

        ObjectMapper objectMapper = new ObjectMapper();
        KakaoProfile kakaoProfile = objectMapper.readValue(profileResponse.getBody(), KakaoProfile.class);
        return kakaoProfile;
    }

    @Transactional(readOnly = true)
    public User getUser(HttpServletRequest request) {   // 인증된 사용자 정보 가져오기
        String socialId = (String) request.getAttribute("socialId");
        User user = userRepository.findBySocialId(socialId);
        System.out.println("====현재 로그인 중인 사용자====");
        System.out.println(user);
        return user;
    }
}

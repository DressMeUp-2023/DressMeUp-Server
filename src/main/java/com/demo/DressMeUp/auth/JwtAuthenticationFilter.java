package com.demo.DressMeUp.auth;

import com.auth0.jwt.JWT;
import com.demo.DressMeUp.domain.user.domain.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.auth0.jwt.algorithms.Algorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;

    @Override  // login요청시 실행
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            // json 데이터 파싱하기
            ObjectMapper om = new ObjectMapper();
            User user = om.readValue(request.getInputStream(), User.class);
            System.out.println("JwtAuthenticationFilter - Attempting authentication for user: " + user.getUserId());

//            System.out.println("JwtAuthenticationFilter: " + user);
//            System.out.println(user.getUserId());
//            System.out.println(user.getPassword());

            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user.getUserId(), user.getPassword());
            Authentication authentication =
                    authenticationManager.authenticate(authenticationToken);

            PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
            System.out.println("로그인 완료, 유저이름: " + principalDetails.getUser().getUserId());

            return authentication;  // 세션에 저장된다

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        System.out.println("successfulAuthentication이 실행됨 : 인증 완료");

        PrincipalDetails principalDetails = (PrincipalDetails) authResult.getPrincipal();

        // JWT 라이브러리 이용
        String jwtToken = JWT.create()
                .withSubject("토큰 발급")
                .withExpiresAt(new Date(System.currentTimeMillis() + JwtProperties.EXPIRATION_TIME ))  // 만료시간(1000=1초)
                .withClaim("id", principalDetails.getUser().getId())
                .withClaim("nickname", principalDetails.getUser().getNickname())
                .sign(Algorithm.HMAC512("dressmeup"));  // 내 서버만 아는 고유 시크릿키
//        System.out.println(jwtToken);
        response.addHeader(JwtProperties.HEADER_STRING, JwtProperties.TOKEN_PREFIX+jwtToken);  // 헤더에 담겨 사용자에게 응답된다
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        // 인증 실패 로그 추가
        System.out.println("JwtAuthenticationFilter - Authentication failed: " + failed.getMessage());

    }
}

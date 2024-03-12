package com.demo.DressMeUp.domain.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SignUpReq {

    private boolean authenticatied;  // 휴대폰 인증 일치 여부
    private String role = "ROLE_USER";


    private String nickname;

    private String gender;

    private String userId;

    private String password;

    private String phonenumber;

    private String image;
}

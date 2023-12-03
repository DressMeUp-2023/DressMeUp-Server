package com.demo.DressMeUp.domain.user.dto;

import com.demo.DressMeUp.domain.user.domain.User;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginRes {

    private Long id;
    private String nickname;

    public static LoginRes from(User user) {
        return LoginRes.builder()
                .id(user.getId())
                .nickname(user.getNickname()).build();
    }
}

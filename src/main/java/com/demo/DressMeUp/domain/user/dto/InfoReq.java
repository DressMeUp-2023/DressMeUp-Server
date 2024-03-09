package com.demo.DressMeUp.domain.user.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class InfoReq {

    private String nickname;

    private String password;
}

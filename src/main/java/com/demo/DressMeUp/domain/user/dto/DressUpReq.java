package com.demo.DressMeUp.domain.user.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DressUpReq {

    private Long topId;
    private Long bottomId;
    private Long dressId;

    private int count;  // 옷 입은 갯수
}

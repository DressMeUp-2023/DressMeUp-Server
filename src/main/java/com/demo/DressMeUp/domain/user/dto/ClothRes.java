package com.demo.DressMeUp.domain.user.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ClothRes {

    private Long id;  // 사용자id
    private String image;
}

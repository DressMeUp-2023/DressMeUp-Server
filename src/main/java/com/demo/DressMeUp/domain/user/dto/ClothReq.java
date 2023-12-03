package com.demo.DressMeUp.domain.user.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ClothReq {

    private Long userId;
    private String type;  // top, bottom, dress
}

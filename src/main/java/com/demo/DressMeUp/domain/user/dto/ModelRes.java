package com.demo.DressMeUp.domain.user.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ModelRes {

    private Long id;
    private String nickname;
    private String modelImage;

}

package com.demo.DressMeUp.domain.cloth.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ClothReq {

    private String imageUrl;
    private String type;
}

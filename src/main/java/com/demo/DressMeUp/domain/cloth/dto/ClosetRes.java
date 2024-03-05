package com.demo.DressMeUp.domain.cloth.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

@Builder
public class ClosetRes {

    @JsonProperty("imageUrl")
    private String imageUrl;
}

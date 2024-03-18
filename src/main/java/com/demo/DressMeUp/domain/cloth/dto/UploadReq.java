package com.demo.DressMeUp.domain.cloth.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UploadReq {

    private String type;  // top, bottom, dress

    private String original;

}

package com.demo.DressMeUp.domain.cloth.api;

import com.demo.DressMeUp.auth.PrincipalDetails;
import com.demo.DressMeUp.domain.cloth.application.ClothService;
import com.demo.DressMeUp.global.common.BaseException;
import com.demo.DressMeUp.global.common.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.demo.DressMeUp.global.common.BaseResponseStatus.*;

@RestController
@RequiredArgsConstructor
public class ClothController {

    private final ClothService clothService;


    @PatchMapping("/auth/{imageId}/image-like")
    public BaseResponse imageLike(Authentication authentication, @PathVariable Long imageId) {
        try {
            PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
            Boolean status =  clothService.imageLike(principalDetails.getUser().getId(), imageId);
            if (status) {
                return new BaseResponse(ADD_LIKE);
            } else {
                return new BaseResponse(DELETE_LIKE);
            }
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }

}

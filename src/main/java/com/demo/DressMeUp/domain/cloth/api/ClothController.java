package com.demo.DressMeUp.domain.cloth.api;

import com.demo.DressMeUp.auth.PrincipalDetails;
import com.demo.DressMeUp.domain.cloth.application.ClothService;
import com.demo.DressMeUp.domain.cloth.dto.ClosetRes;
import com.demo.DressMeUp.domain.cloth.dto.ClothReq;
import com.demo.DressMeUp.global.common.BaseException;
import com.demo.DressMeUp.global.common.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/auth/closet")
    public BaseResponse<ClosetRes> getCloset(Authentication authentication) throws BaseException{

        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
        return new BaseResponse(clothService.getCloset(principalDetails.getUser().getId()));

    }

    @PostMapping("/auth/image")
    public BaseResponse saveCloth(Authentication authentication, @RequestBody ClothReq clothReq) throws BaseException {
        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
        clothService.saveCloth(principalDetails.getUser(), clothReq);
        return new BaseResponse(CLOTH_UPDATED);
    }

//    /*
//    옷장 조회하기(short, trouser, top, dress 나눠서)
//     */
//
//    @GetMapping("/auth/closet/l-skirt")
//    public BaseResponse<ClosetRes> getClosetLSkirt(Authentication authentication) throws BaseException{
//
//        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
//        return new BaseResponse(clothService.getClosetLSkirt(principalDetails.getUser().getId()));
//
//    }
//
//    @GetMapping("/auth/closet/s-skirt")
//    public BaseResponse<ClosetRes> getClosetSSkirt(Authentication authentication) throws BaseException{
//
//        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
//        return new BaseResponse(clothService.getClosetSSkirt(principalDetails.getUser().getId()));
//
//    }
//    @GetMapping("/auth/closet/short")
//    public BaseResponse<ClosetRes> getClosetShort(Authentication authentication) throws BaseException{
//
//        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
//        return new BaseResponse(clothService.getClosetShort(principalDetails.getUser().getId()));
//
//    }
//    @GetMapping("/auth/closet/trouser")
//    public BaseResponse<ClosetRes> getClosetTrouser(Authentication authentication) throws BaseException{
//
//        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
//        return new BaseResponse(clothService.getClosetTrouser(principalDetails.getUser().getId()));
//
//    }
//
//
//    @GetMapping("/auth/closet/top")
//    public BaseResponse<ClosetRes> getClosetTop(Authentication authentication) throws BaseException{
//
//        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
//        return new BaseResponse(clothService.getClosetTop(principalDetails.getUser().getId()));
//
//    }
//
//    @GetMapping("/auth/closet/dress")
//    public BaseResponse<ClosetRes> getClosetDress(Authentication authentication) throws BaseException{
//
//        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
//        return new BaseResponse(clothService.getClosetDress(principalDetails.getUser().getId()));
//
//    }
}

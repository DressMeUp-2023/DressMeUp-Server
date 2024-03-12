package com.demo.DressMeUp.domain.user.api;

import com.demo.DressMeUp.auth.PrincipalDetails;
import com.demo.DressMeUp.domain.user.application.UserService;
import com.demo.DressMeUp.domain.user.dto.*;
import com.demo.DressMeUp.global.common.BaseException;
import com.demo.DressMeUp.global.common.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Random;

import static com.demo.DressMeUp.global.common.BaseResponseStatus.*;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/sign-up")
    public BaseResponse<LoginRes> signup(@RequestBody SignUpReq signUpReq) {
        try {
            System.out.println("Controller: " + signUpReq);
            return new BaseResponse(userService.signup(signUpReq));
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }

    }

    @PatchMapping("/auth/model")
    public BaseResponse<ModelRes> selectModel(Authentication authentication,@RequestBody ImageReq imageReq) throws BaseException{

        try {
            PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
            System.out.println(principalDetails.getUser().getId());
            return new BaseResponse(userService.selectModel(principalDetails.getUser().getId(), imageReq));
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }

    }

    @PostMapping("/auth/clothes")
    public BaseResponse<ClothRes> uploadClothes(Authentication authentication, @RequestPart ClothReq clothReq, @RequestPart(value="image")MultipartFile multipartFile) throws BaseException {
        try {
            PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
            return new BaseResponse(userService.uploadClothes(principalDetails.getUser(), clothReq, multipartFile));
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }

//    @PostMapping("/auth/dress-up")
//    public BaseResponse dressUp(Authentication authentication, @RequestBody DressUpReq dressUpReq) {
//        try {
//            PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
//            userService.dressUp(dressUpReq, principalDetails.getUser().getId());
//            return new BaseResponse(DRESS_UP_COMPLETED);
//        } catch (BaseException e) {
//            return new BaseResponse<>(e.getStatus());
//        }
//    }

    @PatchMapping("/auth/mypage")
    public BaseResponse myPage(Authentication authentication, @RequestBody InfoReq infoReq) {
        try {
            PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
            userService.myPage(principalDetails.getUser(), infoReq);
            return new BaseResponse(MY_PAGE_INFO_UPDATED);
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }


}
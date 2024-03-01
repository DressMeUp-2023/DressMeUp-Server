package com.demo.DressMeUp.domain.user.api;

import com.demo.DressMeUp.auth.PrincipalDetails;
import com.demo.DressMeUp.domain.cloth.domain.Dress;
import com.demo.DressMeUp.domain.user.application.UserService;
import com.demo.DressMeUp.domain.user.dto.*;
import com.demo.DressMeUp.global.common.BaseException;
import com.demo.DressMeUp.global.common.BaseResponse;
import com.demo.DressMeUp.global.common.BaseResponseStatus;
import lombok.RequiredArgsConstructor;
import net.nurigo.sdk.NurigoApp;
import net.nurigo.sdk.message.model.Message;
import net.nurigo.sdk.message.request.SingleMessageSendingRequest;
import net.nurigo.sdk.message.response.SingleMessageSentResponse;
import net.nurigo.sdk.message.service.DefaultMessageService;
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

            return new BaseResponse(userService.signup(signUpReq));
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }

    }

    @PostMapping("/auth/model")
    public BaseResponse<ModelRes> selectModel(Authentication authentication,@RequestPart(value="image")MultipartFile multipartFile) throws BaseException{

        try {
            PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
            System.out.println(principalDetails.getUser().getId());
            return new BaseResponse(userService.selectModel(principalDetails.getUser().getId(), multipartFile));
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }

    }

    @PostMapping("/clothes")
    public BaseResponse<ClothRes> uploadClothes(@RequestPart ClothReq clothReq, @RequestPart(value="image")MultipartFile multipartFile) throws BaseException {
        try {
            return new BaseResponse(userService.uploadClothes(clothReq, multipartFile));
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }

    @PostMapping("/{userId}/dress-up")
    public BaseResponse dressUp(@RequestBody DressUpReq dressUpReq, @PathVariable Long userId) {
        try {
            userService.dressUp(dressUpReq, userId);
            return new BaseResponse(DRESS_UP_COMPLETED);
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }


}
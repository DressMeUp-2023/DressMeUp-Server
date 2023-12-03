package com.demo.DressMeUp.domain.user.api;

import com.demo.DressMeUp.domain.user.application.UserService;
import com.demo.DressMeUp.domain.user.dto.LoginRes;
import com.demo.DressMeUp.domain.user.dto.SignUpReq;
import com.demo.DressMeUp.global.common.BaseException;
import com.demo.DressMeUp.global.common.BaseResponse;
import com.demo.DressMeUp.global.common.BaseResponseStatus;
import lombok.RequiredArgsConstructor;
import net.nurigo.sdk.NurigoApp;
import net.nurigo.sdk.message.model.Message;
import net.nurigo.sdk.message.request.SingleMessageSendingRequest;
import net.nurigo.sdk.message.response.SingleMessageSentResponse;
import net.nurigo.sdk.message.service.DefaultMessageService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

import static com.demo.DressMeUp.global.common.BaseResponseStatus.FAILED_TO_SIGNUP;
import static com.demo.DressMeUp.global.common.BaseResponseStatus.SUCCESS_TO_SIGNUP;

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

}

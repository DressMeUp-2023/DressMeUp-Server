package com.demo.DressMeUp.domain.user.api;

import com.demo.DressMeUp.global.common.BaseResponse;
import lombok.RequiredArgsConstructor;
import net.nurigo.sdk.NurigoApp;
import net.nurigo.sdk.message.model.Message;
import net.nurigo.sdk.message.request.SingleMessageSendingRequest;
import net.nurigo.sdk.message.response.SingleMessageSentResponse;
import net.nurigo.sdk.message.service.DefaultMessageService;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;
import java.util.Random;

import static com.demo.DressMeUp.global.common.BaseResponseStatus.ACCESSED_CODE;
import static com.demo.DressMeUp.global.common.BaseResponseStatus.DENIED_CODE;

@RestController
@RequiredArgsConstructor
public class MessageController {

    private final DefaultMessageService messageService;
    private static String confirmNum;

    public MessageController() {
        this.messageService = NurigoApp.INSTANCE.initialize("NCS4WTXMO3JH9YJZ", "DRZUHZGGFQRVM51IVRAHPZA9LNBIDBLW", "https://api.coolsms.co.kr");

    }

    @PostMapping("/send-code")
    public SingleMessageSentResponse sendOne(@RequestParam("toPhone") String toNumber) {

        Random rand = new Random();
        String verificationCode = "";
        for (int i=0; i<6; i++) {
            String num = Integer.toString(rand.nextInt(10));
            verificationCode += num;
        }

        Message message = new Message();
        message.setFrom("01085593853");
        message.setTo(toNumber);
        message.setText("[DressMeUp] 인증번호 [" + verificationCode + "] 를 입력해주세요.");

        confirmNum = verificationCode;
        System.out.println("verificationCode : " + verificationCode);
        System.out.println("confirmNum : " + confirmNum);
        SingleMessageSentResponse response = this.messageService.sendOne(new SingleMessageSendingRequest(message));

        return response;

    }

    @PostMapping("/confirm/code") // 인증번호 입력받으면 confirmNum이랑 일치하는지 확인, true false 반환?
    public BaseResponse confirmCode(@RequestBody String confirmCode) {
        System.out.println("confirmNum : " + confirmNum);
        System.out.println("confirmCode : " + confirmCode);

        if (confirmNum == confirmCode) {
            return new BaseResponse(ACCESSED_CODE);
        } else{
            return new BaseResponse(DENIED_CODE);
        }
    }
}

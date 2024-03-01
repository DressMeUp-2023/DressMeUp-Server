package com.demo.DressMeUp.domain.user.api;

import lombok.RequiredArgsConstructor;
import net.nurigo.sdk.NurigoApp;
import net.nurigo.sdk.message.model.Message;
import net.nurigo.sdk.message.request.SingleMessageSendingRequest;
import net.nurigo.sdk.message.response.SingleMessageSentResponse;
import net.nurigo.sdk.message.service.DefaultMessageService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

@RestController
@RequiredArgsConstructor
public class MessageController {

    private final DefaultMessageService messageService;

    public MessageController() {
        this.messageService = NurigoApp.INSTANCE.initialize("NCSNFZ8HGOG1MMZD", "KHBW2YGT0UDUQ41FKECXYKFJIQ0YO6MT", "https://api.coolsms.co.kr");

    }

    @PostMapping("/send-one")
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

        SingleMessageSentResponse response = this.messageService.sendOne(new SingleMessageSendingRequest(message));

        return response;

    }
}

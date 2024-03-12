package com.demo.DressMeUp.domain.user.api;

import com.demo.DressMeUp.domain.user.UserRepository;
import com.demo.DressMeUp.global.common.BaseException;
import com.demo.DressMeUp.global.common.BaseResponse;
import lombok.RequiredArgsConstructor;
import net.nurigo.sdk.NurigoApp;
import net.nurigo.sdk.message.model.Message;
import net.nurigo.sdk.message.request.SingleMessageSendingRequest;
import net.nurigo.sdk.message.response.SingleMessageSentResponse;
import net.nurigo.sdk.message.service.DefaultMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Random;

import static com.demo.DressMeUp.global.common.BaseResponseStatus.*;

@RestController
@RequiredArgsConstructor
public class MessageController {

    private final DefaultMessageService messageService;
    private final UserRepository userRepository;
    private static String confirmNum;

    @Autowired
    public MessageController(UserRepository userRepository) {
        this.messageService = NurigoApp.INSTANCE.initialize("NCSNFZ8HGOG1MMZD", "KHBW2YGT0UDUQ41FKECXYKFJIQ0YO6MT", "https://api.coolsms.co.kr");
        this.userRepository = userRepository;
    }

    @PostMapping("/send-code")
    public SingleMessageSentResponse sendOne(@RequestParam("toPhone") String toNumber) throws BaseException {

        // 해당 번호로 가입한 사용자 있는지 검증
        if (userRepository.existsByPhonenum(toNumber)) {
            throw new BaseException(PHONENUM_ALREADY_EXISTS);
        }

        Random rand = new Random();
        String verificationCode = "";
        for (int i = 0; i < 6; i++) {
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

    @PostMapping("/confirm/code")  // 인증번호 입력받으면 confirmNum이랑 일치하는지 확인, true false 반환
    public BaseResponse confirmCode(@RequestBody Map<String, String> requestBody) {
        String receivedConfirmCode = requestBody.get("confirmCode");

        System.out.println("confirmNum : " + confirmNum);
        System.out.println("confirmCode : " + receivedConfirmCode);

        if (confirmNum.trim().equals(receivedConfirmCode.trim())) {
            return new BaseResponse(ACCESSED_CODE);
        } else {
            return new BaseResponse(DENIED_CODE);
        }
    }
}

package com.demo.DressMeUp.domain.user.application;

import com.demo.DressMeUp.domain.user.UserRepository;
import com.demo.DressMeUp.domain.user.domain.Gender;
import com.demo.DressMeUp.domain.user.domain.User;
import com.demo.DressMeUp.domain.user.dto.LoginRes;
import com.demo.DressMeUp.domain.user.dto.SignUpReq;
import com.demo.DressMeUp.global.common.BaseException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.demo.DressMeUp.global.common.BaseResponseStatus.FAILED_TO_SIGNUP;
import static com.demo.DressMeUp.global.common.BaseResponseStatus.NICKNAME_ALREADY_EXISTS;


@Slf4j
@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public LoginRes signup(SignUpReq signUpReq) throws BaseException {

        if (userRepository.existsByNickname(signUpReq.getNickname())) {
            throw new BaseException(NICKNAME_ALREADY_EXISTS);

        }

        try {
            User loginUser = userRepository.save(User.builder()
                    .phonenum(signUpReq.getPhonenumber())
                    .authenticated(true)
                    .nickname(signUpReq.getNickname())
                    .gender(Gender.valueOf(signUpReq.getGender()))
                    .build());
            log.info("유저 : " + signUpReq.getNickname() +" 회원가입에 성공했습니다");
            return LoginRes.from(loginUser);
        } catch (Exception e) {
            throw new BaseException(FAILED_TO_SIGNUP);
        }

    }
}

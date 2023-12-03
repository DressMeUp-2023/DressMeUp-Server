package com.demo.DressMeUp.domain.user.application;

import com.demo.DressMeUp.domain.cloth.BottomRepository;
import com.demo.DressMeUp.domain.cloth.DressRepository;
import com.demo.DressMeUp.domain.cloth.TopRepository;
import com.demo.DressMeUp.domain.cloth.domain.Bottom;
import com.demo.DressMeUp.domain.cloth.domain.Dress;
import com.demo.DressMeUp.domain.cloth.domain.Top;
import com.demo.DressMeUp.domain.user.UserModelRepository;
import com.demo.DressMeUp.domain.user.UserRepository;
import com.demo.DressMeUp.domain.user.domain.Gender;
import com.demo.DressMeUp.domain.user.domain.User;
import com.demo.DressMeUp.domain.user.domain.UserModel;
import com.demo.DressMeUp.domain.user.dto.*;
import com.demo.DressMeUp.global.common.BaseException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

import static com.demo.DressMeUp.global.common.BaseResponseStatus.*;


@Slf4j
@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final S3UploadService s3UploadService;
    private final UserModelRepository userModelRepository;
    private final BottomRepository bottomRepository;
    private final TopRepository topRepository;
    private final DressRepository dressRepository;

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

            UserModel userModel = UserModel.builder()
                    .user(loginUser)
                    .image("")
                    .build();
            userModel.GenderImage(signUpReq.getGender());

            userModelRepository.save(userModel);
            return LoginRes.from(loginUser);
        } catch (Exception e) {
            throw new BaseException(FAILED_TO_SIGNUP);
        }

    }

    @Transactional
    public ModelRes selectModel(Long userId, MultipartFile multipartFile) throws BaseException {

        if (!userRepository.existsById(userId)) {
            throw new BaseException(NO_USER_FOUND);
        }

        try {
            User loginUser = userRepository.findById(userId).get();
            String modelImage = s3UploadService.upload(multipartFile, "userModel");

            Optional<UserModel> byUserId = userModelRepository.findByUserId(loginUser.getId());
            byUserId.get().changeImage(modelImage);
//            UserModel userModel = userModelRepository.save(UserModel.builder()
//                    .user(loginUser)
//                    .image(modelImage).build());

            return ModelRes.builder()
                    .id(loginUser.getId())
                    .nickname(loginUser.getNickname())
                    .modelImage(modelImage)
                    .build();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Transactional
    public ClothRes uploadClothes(ClothReq clothReq, MultipartFile multipartFile) throws BaseException {
        if (!userRepository.existsById(clothReq.getUserId())) {
            throw new BaseException(NO_USER_FOUND);
        }
        String url = "";
        try {
            if (clothReq.getType().equals("BOTTOM")) {
                String imageUrl = s3UploadService.upload(multipartFile, "bottom");
                Bottom bottom = bottomRepository.save(Bottom.builder()
                                .image(imageUrl)
                                .user_id(clothReq.getUserId())
                                .build());
                url = imageUrl;
            } else if (clothReq.getType().equals("TOP")) {
                String imageUrl = s3UploadService.upload(multipartFile, "top");
                Top top = topRepository.save(Top.builder()
                        .image(imageUrl)
                        .user_id(clothReq.getUserId())
                        .build());
                url = imageUrl;
            } else if (clothReq.getType().equals("DRESS")) {
                String imageUrl = s3UploadService.upload(multipartFile, "dress");
                Dress dress = dressRepository.save(Dress.builder()
                        .image(imageUrl)
                        .user_id(clothReq.getUserId())
                        .build());
                url = imageUrl;
            } else {
                throw new BaseException(NO_CLOTHES_TYPE);
            }

            return ClothRes.builder()
                    .id(clothReq.getUserId())
                    .image(url).build();


        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

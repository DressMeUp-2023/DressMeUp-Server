package com.demo.DressMeUp.domain.user.application;

import com.demo.DressMeUp.domain.cloth.*;
import com.demo.DressMeUp.domain.cloth.domain.*;
import com.demo.DressMeUp.domain.user.UserModelRepository;
import com.demo.DressMeUp.domain.user.UserRepository;
import com.demo.DressMeUp.domain.user.domain.Gender;
import com.demo.DressMeUp.domain.user.domain.User;
import com.demo.DressMeUp.domain.user.domain.UserModel;
import com.demo.DressMeUp.domain.user.dto.*;
import com.demo.DressMeUp.global.common.BaseException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
    private final BCryptPasswordEncoder passwordEncoder;
    private final UserModelRepository userModelRepository;
    private final ClothRepository clothRepository;
    private final AlbumRepository albumRepository;

    @Transactional
    public LoginRes signup(SignUpReq signUpReq, MultipartFile multipartFile) throws BaseException {

        if (userRepository.existsByNickname(signUpReq.getNickname())) {
            throw new BaseException(NICKNAME_ALREADY_EXISTS);

        }

        if (userRepository.existsByPhonenum(signUpReq.getPhonenumber())) {
            throw new BaseException(PHONENUM_ALREADY_EXISTS);
        }

        try {

            String encodedPassword = passwordEncoder.encode(signUpReq.getPassword());
            String modelImage = s3UploadService.upload(multipartFile, "userModel");  // 이미지 업로드

            User loginUser = userRepository.save(User.builder()
                    .phonenum(signUpReq.getPhonenumber())
                    .authenticated(true)
                    .roles(signUpReq.getRole())
                    .userId(signUpReq.getUserId())
                    .password(encodedPassword)
                    .nickname(signUpReq.getNickname())
                    .gender(Gender.valueOf(signUpReq.getGender()))
                    .build());
            log.info("유저 : " + signUpReq.getNickname() +" 회원가입에 성공했습니다");

//            System.out.println("이미지: " +modelImage);
            UserModel userModel = UserModel.builder()
                    .user(loginUser)
                    .image(modelImage)
                    .build();

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
            String modelImage = s3UploadService.upload(multipartFile, "userModel");  // 이미지 업로드

            Optional<UserModel> byUserId = userModelRepository.findByUserId(loginUser.getId());
            byUserId.get().changeImage(modelImage);

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
    public ClothRes uploadClothes(User user, ClothReq clothReq, MultipartFile multipartFile) throws BaseException {
        if (!userRepository.existsById(user.getId())) {
            throw new BaseException(NO_USER_FOUND);
        }
        String url = "";
        try {
            if (clothReq.getType().equals("SHORT")) {
                String imageUrl = s3UploadService.upload(multipartFile, "short");
                clothRepository.save(Cloth.builder()
                            .image(imageUrl)
                            .user(user)
                            .clothType(ClothType.fromType(clothReq.getType()))
                            .build());
                url = imageUrl;
            } else if (clothReq.getType().equals("TROUSER")) {
                String imageUrl = s3UploadService.upload(multipartFile, "trouser");
                clothRepository.save(Cloth.builder()
                        .image(imageUrl)
                        .user(user)
                        .clothType(ClothType.fromType(clothReq.getType()))
                        .build());
                url = imageUrl;
            }
            else if (clothReq.getType().equals("TOP")) {
                String imageUrl = s3UploadService.upload(multipartFile, "top");
                clothRepository.save(Cloth.builder()
                        .image(imageUrl)
                        .user(user)
                        .clothType(ClothType.fromType(clothReq.getType()))
                        .build());
                url = imageUrl;
            } else if (clothReq.getType().equals("DRESS")) {
                String imageUrl = s3UploadService.upload(multipartFile, "dress");
                clothRepository.save(Cloth.builder()
                        .image(imageUrl)
                        .user(user)
                        .clothType(ClothType.fromType(clothReq.getType()))
                        .build());
                url = imageUrl;
            } else {
                throw new BaseException(NO_CLOTHES_TYPE);
            }

            return ClothRes.builder()
                    .id(user.getId())
                    .image(url).build();


        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

//    @Transactional
//    public void dressUp(DressUpReq dressUpReq, Long userId) throws BaseException {
//
//        if (!userRepository.existsById(userId)) {
//            throw new BaseException(NO_USER_FOUND);
//        }
//
//        User loginUser = userRepository.findById(userId).get();
//
//        if (dressUpReq.getCount() == 2) {
//            if (dressUpReq.getTopId() == null || dressUpReq.getBottomId() == null) {
//                throw new BaseException(NOT_COMPLETED_DRESSING);
//            }
//            albumRepository.save(Album.builder()
//                    .user(loginUser)
//                    .top_id(dressUpReq.getTopId())
//                    .bottoms_id(dressUpReq.getBottomId())
//                    .dress_id(null).build());
//        } else {
//            albumRepository.save(Album.builder()
//                    .user(loginUser)
//                    .top_id(null)
//                    .bottoms_id(null)
//                    .dress_id(dressUpReq.getDressId()).build());
//        }
//
//    }

    @Transactional
    public void myPage(User user, InfoReq infoReq) throws BaseException{
        if (!userRepository.existsById(user.getId())) {
            throw new BaseException(NO_USER_FOUND);
        }
        String encodedPassword = passwordEncoder.encode(infoReq.getPassword());
        user.updateInfo(infoReq.getNickname(), encodedPassword);
        userRepository.save(user);
    }
}
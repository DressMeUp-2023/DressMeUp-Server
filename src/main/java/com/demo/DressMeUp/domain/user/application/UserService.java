package com.demo.DressMeUp.domain.user.application;

import com.demo.DressMeUp.domain.cloth.*;
import com.demo.DressMeUp.domain.cloth.domain.*;
import com.demo.DressMeUp.domain.cloth.dto.UploadReq;
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
    public LoginRes signup(SignUpReq signUpReq) throws BaseException {
        System.out.println("signup: " + signUpReq);

        if (userRepository.existsByNickname(signUpReq.getNickname())) {
            throw new BaseException(NICKNAME_ALREADY_EXISTS);

        }

        try {
            System.out.println("try: " + signUpReq);

            String encodedPassword = passwordEncoder.encode(signUpReq.getPassword());

            User newUser = User.builder()
                    .phonenum(signUpReq.getPhonenumber())
                    .authenticated(true)
                    .roles(signUpReq.getRole())
                    .userId(signUpReq.getUserId())
                    .password(encodedPassword)
                    .nickname(signUpReq.getNickname())
                    .gender(Gender.valueOf(signUpReq.getGender()))
                    .build();
            userRepository.save(newUser);

            log.info("유저 : " + signUpReq.getNickname() +" 회원가입에 성공했습니다");

            UserModel userModel = UserModel.builder()
                    .user(newUser)
                    .image(signUpReq.getImage())
                    .build();

            userModelRepository.save(userModel);
            return LoginRes.from(newUser);
        } catch (Exception e) {
            System.out.println(e);
            throw new BaseException(FAILED_TO_SIGNUP);
        }

    }

    @Transactional
    public ModelRes getModel(Long userId) throws BaseException {

        if (!userRepository.existsById(userId)) {
            throw new BaseException(NO_USER_FOUND);
        }

        User loginUser = userRepository.findById(userId).get();
        Optional<UserModel> byUserId = userModelRepository.findByUserId(loginUser.getId());

        return ModelRes.builder()
                .id(loginUser.getId())
                .nickname(loginUser.getNickname())
                .modelImage(byUserId.get().getImage())
                .build();

    }

    @Transactional
    public String imageReturn(MultipartFile multipartFile) throws BaseException, IOException {
        return s3UploadService.upload(multipartFile, "model");

    }

    @Transactional
    public ModelRes selectModel(Long userId, ImageReq imageReq) throws BaseException {

        if (!userRepository.existsById(userId)) {
            throw new BaseException(NO_USER_FOUND);
        }

        User loginUser = userRepository.findById(userId).get();
        Optional<UserModel> byUserId = userModelRepository.findByUserId(loginUser.getId());
        byUserId.get().changeImage(imageReq.getImage());

        return ModelRes.builder()
                .id(loginUser.getId())
                .nickname(loginUser.getNickname())
                .modelImage(imageReq.getImage())
                .build();

    }

    @Transactional
    public ClothRes uploadClothes(User user, UploadReq uploadReq, MultipartFile multipartFile) throws BaseException {
        if (!userRepository.existsById(user.getId())) {
            throw new BaseException(NO_USER_FOUND);
        }
        String url = "";
        try {
            if (uploadReq.getType().equals("TOP")) {
                String imageUrl = s3UploadService.upload(multipartFile, "TOP");
                clothRepository.save(Cloth.builder()
                            .image(imageUrl)
                            .user(user)
                            .original(uploadReq.getOriginal())
                            .clothType(ClothType.fromType(uploadReq.getType()))
                            .build());
                url = imageUrl;
            } else if (uploadReq.getType().equals("DRESS")) {
                String imageUrl = s3UploadService.upload(multipartFile, "DRESS");
                clothRepository.save(Cloth.builder()
                        .image(imageUrl)
                        .user(user)
                        .original(uploadReq.getOriginal())
                        .clothType(ClothType.fromType(uploadReq.getType()))
                        .build());
                url = imageUrl;
            }
            else if (uploadReq.getType().equals("BOTTOM")) {
                String imageUrl = s3UploadService.upload(multipartFile, "BOTTOM");
                clothRepository.save(Cloth.builder()
                        .image(imageUrl)
                        .user(user)
                        .original(uploadReq.getOriginal())
                        .clothType(ClothType.fromType(uploadReq.getType()))
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

    @Transactional
    public void saveModel(User user, String model) {
        UserModel userModel = UserModel.builder()
                .user(user)
                .image(model)
                .build();

        userModelRepository.save(userModel);
    }
}
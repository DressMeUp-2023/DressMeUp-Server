package com.demo.DressMeUp.domain.cloth.application;

import com.demo.DressMeUp.domain.cloth.*;
import com.demo.DressMeUp.domain.cloth.domain.*;
import com.demo.DressMeUp.domain.cloth.dto.ClosetRes;
import com.demo.DressMeUp.domain.user.UserRepository;
import com.demo.DressMeUp.global.common.BaseException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.demo.DressMeUp.global.common.BaseResponseStatus.*;

@RequiredArgsConstructor
@Service
public class ClothService {

    private final UserRepository userRepository;
//    private final TopRepository topRepository;
//    private final BottomRepository bottomRepository;
//    private final DressRepository dressRepository;
    private final AlbumRepository albumRepository;
    private final ClothRepository clothRepository;


    @Transactional
    public Boolean imageLike(Long userId, Long imageId) throws BaseException {
        if (!userRepository.existsById(userId)) {
            throw new BaseException(NO_USER_FOUND);
        }

        Optional<Album> albumOptional = albumRepository.findById(imageId);
        if (albumOptional.isPresent()) {
            Album album = albumOptional.get();
            if (userId != album.getUser().getId()) {
                throw new BaseException(NO_PERMISSION);
            }

            if (album.isImage_like()) {  // 좋아요 표시가 되어있다면
                album.setImage_like(false);
                return false;
            } else {
                album.setImage_like(true);
                return true;
            }
        }
        throw new BaseException(NO_ALBUM_FOUND);
    }

    @Transactional(readOnly = true)
    public List<ClosetRes> getClosetLSkirt(Long userId) {

        List<Cloth> byUserAndType = clothRepository.findByUser_idAndClothType(userId, ClothType.L_SKIRT);
        List<ClosetRes> closetResList = new ArrayList<>();
        for (Cloth cloth: byUserAndType) {
            ClosetRes closetRes = ClosetRes.builder()
                    .imageUrl(cloth.getImage())
                    .build();
            closetResList.add(closetRes);
        }

        return closetResList;
    }

    @Transactional(readOnly = true)
    public List<ClosetRes> getClosetSSkirt(Long userId) {

        List<Cloth> byUserAndType = clothRepository.findByUser_idAndClothType(userId, ClothType.S_SKIRT);
        List<ClosetRes> closetResList = new ArrayList<>();
        for (Cloth cloth: byUserAndType) {
            ClosetRes closetRes = ClosetRes.builder()
                    .imageUrl(cloth.getImage())
                    .build();
            closetResList.add(closetRes);
        }

        return closetResList;
    }
    @Transactional(readOnly = true)
    public List<ClosetRes> getClosetTop(Long userId) {

        List<Cloth> byUserAndType = clothRepository.findByUser_idAndClothType(userId, ClothType.TOP);
        List<ClosetRes> closetResList = new ArrayList<>();
        for (Cloth cloth: byUserAndType) {
            ClosetRes closetRes = ClosetRes.builder()
                    .imageUrl(cloth.getImage())
                    .build();
            closetResList.add(closetRes);
        }

        return closetResList;
    }

    @Transactional(readOnly = true)
    public List<ClosetRes> getClosetShort(Long userId) {

        List<Cloth> byUserAndType = clothRepository.findByUser_idAndClothType(userId, ClothType.SHORT);
        List<ClosetRes> closetResList = new ArrayList<>();
        for (Cloth cloth: byUserAndType) {
            ClosetRes closetRes = ClosetRes.builder()
                    .imageUrl(cloth.getImage())
                    .build();
            System.out.println("short.getImage() : " +cloth.getImage());
            closetResList.add(closetRes);
        }

        return closetResList;
    }

    @Transactional(readOnly = true)
    public List<ClosetRes> getClosetTrouser(Long userId) {

        List<Cloth> byUserAndType = clothRepository.findByUser_idAndClothType(userId, ClothType.TROUSER);
        List<ClosetRes> closetResList = new ArrayList<>();
        for (Cloth cloth: byUserAndType) {
            ClosetRes closetRes = ClosetRes.builder()
                    .imageUrl(cloth.getImage())
                    .build();
            closetResList.add(closetRes);
        }

        return closetResList;
    }

    @Transactional(readOnly = true)
    public List<ClosetRes> getClosetDress(Long userId) {

        List<Cloth> byUserAndType = clothRepository.findByUser_idAndClothType(userId, ClothType.DRESS);
        List<ClosetRes> closetResList = new ArrayList<>();
        for (Cloth cloth: byUserAndType) {
            ClosetRes closetRes = ClosetRes.builder()
                    .imageUrl(cloth.getImage())
                    .build();
            closetResList.add(closetRes);
        }

        return closetResList;
    }

}

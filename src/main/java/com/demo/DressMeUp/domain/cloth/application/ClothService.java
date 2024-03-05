package com.demo.DressMeUp.domain.cloth.application;

import com.demo.DressMeUp.domain.cloth.AlbumRepository;
import com.demo.DressMeUp.domain.cloth.BottomRepository;
import com.demo.DressMeUp.domain.cloth.DressRepository;
import com.demo.DressMeUp.domain.cloth.TopRepository;
import com.demo.DressMeUp.domain.cloth.domain.Album;
import com.demo.DressMeUp.domain.cloth.domain.Bottom;
import com.demo.DressMeUp.domain.cloth.domain.Dress;
import com.demo.DressMeUp.domain.cloth.domain.Top;
import com.demo.DressMeUp.domain.cloth.dto.ClosetRes;
import com.demo.DressMeUp.domain.cloth.dto.ClothReq;
import com.demo.DressMeUp.domain.user.UserRepository;
import com.demo.DressMeUp.domain.user.dto.ClothRes;
import com.demo.DressMeUp.global.common.BaseException;
import kotlin.collections.ArrayDeque;
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
    private final TopRepository topRepository;
    private final BottomRepository bottomRepository;
    private final DressRepository dressRepository;
    private final AlbumRepository albumRepository;


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
    public List<ClosetRes> getClosetTop(Long userId) {

        List<Top> byUser_id = topRepository.findByUser_id(userId);
        List<ClosetRes> closetResList = new ArrayList<>();
        for (Top top: byUser_id) {
            ClosetRes closetRes = ClosetRes.builder()
                    .imageUrl(top.getImage())
                    .build();
            System.out.println("top.getImage() : " +top.getImage());
            closetResList.add(closetRes);
        }

        return closetResList;
    }

    @Transactional(readOnly = true)
    public List<ClosetRes> getClosetBottom(Long userId) {

        List<Bottom> byUser_id = bottomRepository.findByUser_id(userId);
        List<ClosetRes> closetResList = new ArrayList<>();
        for (Bottom bottom: byUser_id) {
            ClosetRes closetRes = ClosetRes.builder()
                    .imageUrl(bottom.getImage())
                    .build();
            System.out.println("bottom.getImage() : " +bottom.getImage());
            closetResList.add(closetRes);
        }

        return closetResList;
    }

    @Transactional(readOnly = true)
    public List<ClosetRes> getClosetDress(Long userId) {

        List<Dress> byUser_id = dressRepository.findByUser_id(userId);
        List<ClosetRes> closetResList = new ArrayList<>();
        for (Dress dress: byUser_id) {
            ClosetRes closetRes = ClosetRes.builder()
                    .imageUrl(dress.getImage())
                    .build();
            System.out.println("dress.getImage() : " +dress.getImage());
            closetResList.add(closetRes);
        }

        return closetResList;
    }

}

package com.demo.DressMeUp.domain.cloth;

import com.demo.DressMeUp.domain.cloth.domain.Cloth;
import com.demo.DressMeUp.domain.cloth.domain.ClothType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClothRepository extends JpaRepository<Cloth, Long> {

    List<Cloth> findByUser_id(Long userId);
    List<Cloth> findByUser_idAndClothType(Long userId, ClothType clothType);
}

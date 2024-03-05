package com.demo.DressMeUp.domain.cloth;

import com.demo.DressMeUp.domain.cloth.domain.Bottom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BottomRepository extends JpaRepository<Bottom, Long> {

    List<Bottom> findByUser_id(Long userId);
}

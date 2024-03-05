package com.demo.DressMeUp.domain.cloth;

import com.demo.DressMeUp.domain.cloth.domain.Top;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TopRepository extends JpaRepository<Top, Long> {

    List<Top> findByUser_id(Long userId);
}

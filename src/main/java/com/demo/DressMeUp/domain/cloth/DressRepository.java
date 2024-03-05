package com.demo.DressMeUp.domain.cloth;

import com.demo.DressMeUp.domain.cloth.domain.Dress;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DressRepository extends JpaRepository<Dress, Long> {

    List<Dress> findByUser_id(Long userId);
}

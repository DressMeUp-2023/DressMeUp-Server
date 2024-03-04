package com.demo.DressMeUp.domain.cloth;

import com.demo.DressMeUp.domain.cloth.domain.Dress;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DressRepository extends JpaRepository<Dress, Long> {
}

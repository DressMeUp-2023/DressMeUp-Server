package com.demo.DressMeUp.domain.cloth;

import com.demo.DressMeUp.domain.cloth.domain.Album;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlbumRepository extends JpaRepository<Album, Long> {
}

package com.demo.DressMeUp.domain.user;

import com.demo.DressMeUp.domain.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByUserId(String userId);

    boolean existsByNickname(String nickname);

    boolean existsByPhonenum(String phonenum);
}
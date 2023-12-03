package com.demo.DressMeUp.domain.user;

import com.demo.DressMeUp.domain.user.domain.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserModelRepository extends JpaRepository<UserModel, Long> {

    Optional<UserModel> findByUserId(Long userId);

}

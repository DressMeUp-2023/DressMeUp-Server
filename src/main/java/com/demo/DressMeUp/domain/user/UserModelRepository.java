package com.demo.DressMeUp.domain.user;

import com.demo.DressMeUp.domain.user.domain.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserModelRepository extends JpaRepository<UserModel, Long> {

}

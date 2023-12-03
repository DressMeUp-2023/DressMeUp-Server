package com.demo.DressMeUp.domain.user.domain;

import com.demo.DressMeUp.domain.user.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Table(name = "user_model")
@Entity
public class UserModel {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    // 기본 이미지 선택하면 들어가는 default값 설정
    private String image;

    public void GenderImage(String gender) {
        if (gender.equals("MALE")) {
            this.image = "https://dressmeup-user.s3.ap-northeast-2.amazonaws.com/%EB%82%A8%EC%9E%90.PNG";
        }
        else
            this.image = "https://dressmeup-user.s3.ap-northeast-2.amazonaws.com/%EC%97%AC%EC%9E%90.PNG";
    }

    public void changeImage(String image) {
        this.image = image;
    }

}

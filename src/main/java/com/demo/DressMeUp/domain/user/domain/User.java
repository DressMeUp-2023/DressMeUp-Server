package com.demo.DressMeUp.domain.user.domain;

import com.demo.DressMeUp.domain.cloth.domain.Album;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Table(name = "user")
@Entity
public class User {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String phonenum;

    private boolean authenticated;  // 핸드폰 인증 여부

    @Column(unique = true)
    private String nickname;

    @OneToMany(mappedBy = "user")
    private List<Album> album;

    @OneToMany(mappedBy = "user")
    private List<UserModel> userModel;

    @Enumerated(EnumType.STRING)
    private Gender gender;

}

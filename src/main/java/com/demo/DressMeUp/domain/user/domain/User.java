package com.demo.DressMeUp.domain.user.domain;

import com.demo.DressMeUp.domain.cloth.domain.Album;
import com.demo.DressMeUp.domain.cloth.domain.Cloth;
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

    @Column(unique = true, nullable = false, length = 20)
    private String userId;  // 아이디

    @Column(nullable = false, length = 20)
    private String password;  // 비밀번호

    private String roles;

    @Column(unique = true, length = 20)
    private String nickname;

    @OneToMany(mappedBy = "user")
    private List<Album> album;

    @OneToMany(mappedBy = "user")
    private List<UserModel> userModel;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @OneToMany(mappedBy = "user")
    private List<Cloth> clothes;

    public void updateInfo(String nickname, String password) {
        this.nickname = nickname;
        this.password = password;
    }
}

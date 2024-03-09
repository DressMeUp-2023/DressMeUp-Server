package com.demo.DressMeUp.domain.cloth.domain;

import com.demo.DressMeUp.domain.user.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Table(name = "album")
@Entity
public class Album {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private String image;

    private boolean image_like;

    public void setImage_like(boolean status) {
        this.image_like = status;
    }
}

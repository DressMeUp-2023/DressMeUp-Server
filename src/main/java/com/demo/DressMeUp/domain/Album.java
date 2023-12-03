package com.demo.DressMeUp.domain;

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

//    private Long user_id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ColumnDefault("null")
    private Long top_id;

    @ColumnDefault("null")
    private Long bottoms_id;

    @ColumnDefault("null")
    private Long dress_id;

}

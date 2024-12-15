package com.ureca.picky_be.jpa.entity.movie;

import com.ureca.picky_be.jpa.entity.config.BaseEntity;
import com.ureca.picky_be.jpa.entity.playlist.Playlist;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Getter
@Entity
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MoviePlaylist extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="playlist_id")
    private Playlist playlist;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="movie_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Movie movie;


}

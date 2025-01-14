package com.ureca.picky_be.jpa.entity.lineReview;


import com.ureca.picky_be.base.business.board.dto.contentDto.AddBoardContentReq;
import com.ureca.picky_be.jpa.entity.board.Board;
import com.ureca.picky_be.jpa.entity.config.BaseEntity;
import com.ureca.picky_be.jpa.entity.config.IsDeleted;
import com.ureca.picky_be.jpa.entity.movie.Movie;
import com.ureca.picky_be.jpa.entity.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(
        name = "line_review_like",
        uniqueConstraints = @UniqueConstraint(columnNames = {"line_review_id", "user_id"})
)
public class LineReviewLike extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="line_review_id")
    private LineReview lineReview;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id")
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Preference preference;

    private boolean isDeleted;

    public LineReviewLike updatePreference(Preference newPreference) {
        this.preference = newPreference;
        return this;
    }

    public static LineReviewLike of(LineReview lineReview, User user, Preference preference) {
        LineReviewLike like = LineReviewLike.builder()
                .lineReview(lineReview)
                .user(user)
                .preference(preference)
                .isDeleted(false)
                .build();

        return like;
    }
}

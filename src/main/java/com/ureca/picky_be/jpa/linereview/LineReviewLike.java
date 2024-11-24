package com.ureca.picky_be.jpa.linereview;


import com.ureca.picky_be.jpa.config.BaseEntity;
import com.ureca.picky_be.jpa.user.User;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
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
    private Preference preference;





}

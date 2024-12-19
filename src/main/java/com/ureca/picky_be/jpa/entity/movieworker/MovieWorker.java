package com.ureca.picky_be.jpa.entity.movieworker;

import com.ureca.picky_be.jpa.entity.config.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Entity
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MovieWorker extends BaseEntity {

    @Id
    private Long id;

    @Column(nullable = false)
    private String name;

    private String profileUrl;
}

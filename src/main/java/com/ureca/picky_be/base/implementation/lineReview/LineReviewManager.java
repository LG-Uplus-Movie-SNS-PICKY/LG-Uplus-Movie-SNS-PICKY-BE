package com.ureca.picky_be.base.implementation.lineReview;

import com.ureca.picky_be.base.business.lineReview.dto.*;
import com.ureca.picky_be.base.business.user.dto.UserLineReviewsReq;
import com.ureca.picky_be.base.persistence.movie.MovieRepository;
import com.ureca.picky_be.base.persistence.user.UserRepository;
import com.ureca.picky_be.base.persistence.lineReview.LineReviewRepository;
import com.ureca.picky_be.global.exception.CustomException;
import com.ureca.picky_be.global.exception.ErrorCode;
import com.ureca.picky_be.global.success.SuccessCode;
import com.ureca.picky_be.jpa.entity.config.IsDeleted;
import com.ureca.picky_be.jpa.entity.lineReview.LineReview;
import com.ureca.picky_be.jpa.entity.lineReview.SortType;
import com.ureca.picky_be.jpa.entity.user.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
@Slf4j
public class LineReviewManager {

    private final LineReviewRepository lineReviewRepository;
    private final MovieRepository movieRepository;
    private final UserRepository userRepository;

    public LineReview createLineReview(CreateLineReviewReq req, Long userId, String userNickname) {
        try {
            if (req.rating() == 0 || req.rating() < 0 || req.rating() > 5) {
                throw new CustomException(ErrorCode.LINEREVIEW_INVALID_RATING);
            }

            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

            boolean exists = lineReviewRepository.existsByMovieIdAndUserId(req.movieId(), user.getId());
            if (exists) {
                throw new CustomException(ErrorCode.LINEREVIEW_CREATE_DUPLICATE);
            }

            LineReview lineReview = LineReview.builder()
                    .userId(userId)
                    .movieId(req.movieId())
                    .rating(req.rating())
                    .writerNickname(userNickname)
                    .context(req.context())
                    .isDeleted(IsDeleted.FALSE)
                    .isSpoiler(req.isSpoiler())
                    .build();
            return lineReviewRepository.save(lineReview);
        } catch (CustomException e) {
            throw e;
        } catch (Exception e) {
            throw new CustomException(ErrorCode.LINEREVIEW_CREATE_FAILED);
        }

    }

    public LineReview updateLineReview(Long lineReviewId, UpdateLineReviewReq req, Long userId) {
        try {
            LineReview existLineReview = lineReviewRepository.findById(lineReviewId)
                    .orElseThrow(() -> new CustomException(ErrorCode.LINEREVIEW_NOT_FOUND));
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

            if (!existLineReview.getUserId().equals(userId)) {
                throw new CustomException(ErrorCode.LINEREVIEW_UPDATE_UNAUTHORIZED);
            }
            existLineReview.lineReviewContextUpdate(req.context(), req.isSpoiler());
            return lineReviewRepository.save(existLineReview);
        } catch (Exception e) {
            throw new CustomException(ErrorCode.LINEREVIEW_UPDATE_FAILED);
        }
    }


    public Slice<LineReviewProjection> findLineReviewsByMovie(Long userId, LineReviewQueryRequest queryReq, PageRequest pageRequest) {
        try {
            Long movieId = queryReq.movieId();
            Long lastReviewId = queryReq.lastReviewId();

            LocalDateTime lastCreatedAt = queryReq.lastCreatedAt();
            SortType sortType = queryReq.sortType();
            if (!movieRepository.existsById(movieId)) {
                throw new CustomException(ErrorCode.MOVIE_NOT_FOUND);
            }

            validateCursor(lastReviewId, lastCreatedAt, sortType);

            switch (sortType) {
                case LIKES:
                    return lineReviewRepository.findByMovieAndLikesCursor(movieId, lastReviewId, lastCreatedAt, userId, pageRequest);
                case LATEST:
                    return lineReviewRepository.findByMovieAndLatestCursor(movieId, lastReviewId, lastCreatedAt, userId, pageRequest);
                default:
                    throw new CustomException(ErrorCode.LINEREVIEW_INVALID_SORTTYPE);
            }
        } catch (CustomException e) {
            throw e;
        } catch (Exception e) {
            throw new CustomException(ErrorCode.LINEREVIEW_GET_FAILED);
        }
    }

    public Slice<MyPageLineReviewProjection> findLineReviewsByNickname(Long requestId, Long currentId, UserLineReviewsReq req, PageRequest pageRequest) {
        Long lastReviewId = req.lastReviewId();
        lastReviewIdValidation(lastReviewId);
        return lineReviewRepository.findByUserIdAndCursor(requestId, currentId, lastReviewId, pageRequest);
    }

    private void lastReviewIdValidation(Long lastReviewId) {
        if(lastReviewId == null) return;
        if(lastReviewId <= 0) {
            throw new CustomException(ErrorCode.LINEREVIEW_INVALID_CURSOR2);
        }
    }


    private void validateCursor(Long lastReviewId, LocalDateTime lastCreatedAt, SortType sortType) {
        // 첫 요청일 경우
        if (lastReviewId == null && lastCreatedAt == null) {
            return;
        }

        // LATEST 정렬에서는 두 값이 모두 필요
        if (sortType == SortType.LATEST) {
            if (lastReviewId == null || lastCreatedAt == null) {
                throw new CustomException(ErrorCode.LINEREVIEW_INVALID_CURSOR1);
            }
        }

        // ID 검증
        if (lastReviewId != null && lastReviewId <= 0) {
            throw new CustomException(ErrorCode.LINEREVIEW_INVALID_CURSOR2);
        }

        // 날짜 검증
        if (lastCreatedAt != null && lastCreatedAt.isAfter(LocalDateTime.now())) {
            throw new CustomException(ErrorCode.LINEREVIEW_INVALID_CURSOR3);
        }
    }

    @Transactional(readOnly = true)
    public RatingLineReviewProjection getTotalRatingfInfo(Long movieId) {
        try {
            return lineReviewRepository.findRatingByMovieId(movieId);
        } catch (Exception e) {
            throw new CustomException(ErrorCode.LINEREVIEW_RATING_QUERY_FAILED);
        }

    }

    @Transactional(readOnly = true)
    public GenderLineReviewProjection getGenderRatingfInfo(Long movieId) {
        try {
            return lineReviewRepository.findGenderRatingByMovieIdAnd(movieId);
        } catch (Exception e) {
            throw new CustomException(ErrorCode.LINEREVIEW_GENDER_QUERY_FAILED);
        }

    }


    public SuccessCode deleteLineReview(Long lineReviewId, Long userId) {
        try {
            Long authorId= lineReviewRepository.findAuthorIdById(lineReviewId);
            if (!authorId.equals(userId)) {
                throw new CustomException(ErrorCode.LINEREVIEW_DELETE_FAILED_USER);
            }
            lineReviewRepository.deleteById(lineReviewId);
            return SuccessCode.DELETE_LINE_REVIEW;
        }
        catch (CustomException e) {
            throw e;
        }
        catch (Exception e){
            throw new CustomException(ErrorCode.LINEREVIEW_DELETE_FAILED);
        }
    }

    public Long getLineReviewCount(Long movieId) {
        return lineReviewRepository.countByMovieId(movieId);
    }
}



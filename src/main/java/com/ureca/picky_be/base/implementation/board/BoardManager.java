package com.ureca.picky_be.base.implementation.board;

import com.ureca.picky_be.base.business.board.dto.BoardCommentProjection;
import com.ureca.picky_be.base.business.board.dto.boardDto.AddBoardReq;
import com.ureca.picky_be.base.business.board.dto.BoardProjection;
import com.ureca.picky_be.base.business.board.dto.boardDto.UpdateBoardReq;
import com.ureca.picky_be.base.business.board.dto.commentDto.GetAllBoardCommentsResp;
import com.ureca.picky_be.base.persistence.board.BoardRepository;
import com.ureca.picky_be.base.persistence.board.BoardCommentRepository;
import com.ureca.picky_be.base.persistence.board.BoardContentRepository;
import com.ureca.picky_be.base.persistence.movie.MovieRepository;
import com.ureca.picky_be.base.persistence.user.UserRepository;
import com.ureca.picky_be.global.exception.CustomException;
import com.ureca.picky_be.global.exception.ErrorCode;
import com.ureca.picky_be.jpa.board.Board;
import com.ureca.picky_be.jpa.board.BoardComment;
import com.ureca.picky_be.jpa.config.IsDeleted;
import com.ureca.picky_be.jpa.movie.Movie;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import static org.apache.commons.lang3.BooleanUtils.TRUE;

@Component
@RequiredArgsConstructor
public class BoardManager {
    private final BoardRepository boardRepository;
    private final MovieRepository movieRepository;
    private final BoardCommentRepository boardCommentRepository;
    private final BoardContentRepository boardContentRepository;
    private final UserRepository userRepository;

    @Transactional
    public void addBoard(Long userId, String userNickname, AddBoardReq req) {
        Movie movie = movieRepository.findById(req.movieId())
                .orElseThrow(() -> new CustomException(ErrorCode.MOVIE_NOT_FOUND));

        if(req.contents().size() > 5) {
            throw new CustomException(ErrorCode.BOARD_CONTENT_OVER_FIVE);
        }

        try {
            Board board = Board.of(userId, movie, req.boardContext(), req.isSpoiler(), req.contents(), userNickname);
            boardRepository.save(board);
        } catch (CustomException e) {
            throw new CustomException(ErrorCode.BOARD_CREATE_FAILED);
        }

    }

    @Transactional
    public void updateBoard(Long boardId, Long userId, UpdateBoardReq req) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new CustomException(ErrorCode.BOARD_NOT_FOUND));

        if(board.getIsDeleted() == IsDeleted.TRUE) throw new CustomException(ErrorCode.BOARD_IS_DELETED);
        try {
            board.updateBoard(req.boardContext(), req.isSpoiler());
            boardRepository.save(board);
        } catch (Exception e) {
            throw new CustomException(ErrorCode.BOARD_UPDATE_FAILED);
        }
    }

    @Transactional(readOnly = true)
    public Slice<BoardProjection> getRecentMovieRelatedBoards(Long userId, Long movieId, Pageable pageable) {
        // 특정 영화 무비로그들 최신순 기준으로 Board들을 가져온다
        if(!movieRepository.existsById(movieId)) throw new CustomException(ErrorCode.MOVIE_NOT_FOUND);

        try {
            Slice<BoardProjection> boards = boardRepository.getRecentMovieRelatedBoards(userId, movieId, pageable);
            return boards;
        } catch(Exception e) {
            throw new CustomException(ErrorCode.BOARD_MOVIE_RELATED_GET_FAILED);
        }
    }

    @Transactional(readOnly = true)
    public Slice<BoardProjection> getRecentMovieBoards(Long userId, Pageable pageable) {
        // 특정 영화 무비로그들 최신순 기준으로 Board들을 가져온다

        try {
            Slice<BoardProjection> boards = boardRepository.getRecentBoards(userId, pageable);
            return boards;
        } catch(Exception e) {
            throw new CustomException(ErrorCode.BOARD_MOVIE_RELATED_GET_FAILED);
        }
    }

    @Transactional(readOnly = true)
    public Slice<BoardCommentProjection> getTenBoardCommentsPerReq(Long boardId, Pageable pageable) {
        // 특정 Board 최신순 기준으로 댓글들을 가져온다
        if(!boardRepository.existsById(boardId)) throw new CustomException(ErrorCode.BOARD_NOT_FOUND);
        if(boardRepository.findIsDeleted(boardId) == IsDeleted.TRUE) throw new CustomException(ErrorCode.BOARD_IS_DELETED);

        try {
            Slice<BoardCommentProjection> comments = boardRepository.getBoardComments(boardId, pageable);
            return comments;
        } catch(Exception e) {
            throw new CustomException(ErrorCode.BOARD_COMMENT_READ_FAILED);
        }
    }

    public void checkBoardWriteUser(Long boardId, Long userId) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new CustomException(ErrorCode.BOARD_NOT_FOUND));

        if(!board.getUserId().equals(userId)) {
            throw new CustomException(ErrorCode.BOARD_USER_NOT_WRITER);
        }
    }
    @Transactional(readOnly = true)
    public void checkBoardCommentWriteUser(Long commentId, Long userId) {
        BoardComment comment = boardCommentRepository.findById(commentId)
                .orElseThrow(() -> new CustomException(ErrorCode.BOARD_COMMENT_NOT_FOUND));

        if(!comment.getUserId().equals(userId)) {
            throw new CustomException(ErrorCode.BOARD_COMMENT_USER_NOT_WRITER);
        }
    }

    @Transactional
    public void addBoardComment(String context, Long boardId, Long userId, String userNickname) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new CustomException(ErrorCode.BOARD_NOT_FOUND));
        if(board.getIsDeleted() == IsDeleted.TRUE) throw new CustomException(ErrorCode.BOARD_IS_DELETED);
        try {
            BoardComment comment = BoardComment.of(board, userId, context, userNickname);
            boardCommentRepository.save(comment);
        } catch (Exception e) {
            throw new CustomException(ErrorCode.BOARD_COMMENT_CREATE_FAILED);
        }
    }

    @Transactional
    public void deleteBoard(Long boardId) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new CustomException(ErrorCode.BOARD_NOT_FOUND));
        if(board.getIsDeleted() == IsDeleted.TRUE) throw new CustomException(ErrorCode.BOARD_IS_DELETED);
        try {
            board.deleteBoard(IsDeleted.fromString("TRUE"));
            boardRepository.save(board);
        } catch (Exception e) {
            throw new CustomException(ErrorCode.BOARD_DELETE_FAILED);
        }
    }


    @Transactional
    public void deleteBoardComment(Long commentId) {
        BoardComment comment = boardCommentRepository.findById(commentId)
                .orElseThrow(() -> new CustomException(ErrorCode.BOARD_COMMENT_NOT_FOUND));
        try{
            boardCommentRepository.deleteByBoardCommentId(commentId);
        }catch (Exception e) {
            throw new CustomException(ErrorCode.BOARD_COMMENT_DELETE_FAILED);
        }
    }

    @Transactional(readOnly = true)
    public void checkBoardIsDeleted(Long boardId) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new CustomException(ErrorCode.BOARD_NOT_FOUND));
        if(board.getIsDeleted() == IsDeleted.TRUE) throw new CustomException(ErrorCode.BOARD_IS_DELETED);
    }
}

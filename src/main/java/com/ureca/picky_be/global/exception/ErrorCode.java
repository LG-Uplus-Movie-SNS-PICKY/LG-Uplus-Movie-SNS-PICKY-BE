package com.ureca.picky_be.global.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;


@Getter
public enum ErrorCode {
    // 기본 에러
    BAD_REQUEST(HttpStatus.BAD_REQUEST, "E001", "잘못된 요청입니다."),
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "E002", "인증에 실패했습니다."),
    FORBIDDEN(HttpStatus.FORBIDDEN, "E003", "접근 권한이 없습니다."),
    NOT_FOUND(HttpStatus.NOT_FOUND, "E004", "리소스를 찾을 수 없습니다."),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "E005", "서버에 문제가 발생했습니다."),
    VALIDATION_ERROR(HttpStatus.BAD_REQUEST, "E006", "입력 값이 유효하지 않습니다."),
    VALIDATION_FAILED(HttpStatus.BAD_REQUEST, "E007", "유효성 검사가 실패했습니다."),
    METHOD_NOT_ALLOWED(HttpStatus.METHOD_NOT_ALLOWED, "E008", "지원하지 않는 요청 메서드입니다."),
    INVALID_DELETED_TYPE(HttpStatus.BAD_REQUEST, "E009","지원하지 않는 타입입니다. TRUE와 FALSE만 가능합니다."),
    LAST_ID_INVALID_CURSOR(HttpStatus.BAD_REQUEST, "E010", "lastId는 0보다 큰 값이어야 합니다."),

    // USER
    USER_SAVE_FAILED(HttpStatus.BAD_REQUEST, "USR001", "유저 저장에 실패했습니다."),
    USER_DELETE_FAILED(HttpStatus.BAD_REQUEST, "USR002", "유저 삭제에 실패했습니다."),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "USR003", "해당 유저가 존재하지 않습니다."),
    USER_UPDATE_FAILED(HttpStatus.BAD_REQUEST, "USR004", "유저 정보 업데이트에 실패했습니다."),
    USER_UPDATE_BAD_REQUEST(HttpStatus.BAD_REQUEST, "USR005", "입력할 정보가 존재하지 않습니다"),
    NO_USER_FOUND(HttpStatus.BAD_REQUEST, "USR006", "해당 조건을 만족하는 유저가 없습니다."),
    NO_DATA_RECEIVED(HttpStatus.BAD_REQUEST, "USR007", "업데이트할 데이터를 받지 못했습니다."),
    USER_SUSPENDED(HttpStatus.BAD_REQUEST, "USR008", "정지된 유저입니다."),

    // GENRE
    GENRE_NOT_FOUND(HttpStatus.NOT_FOUND, "GEN001", "해당 영화 장르를 찾지 못했습니다."),

    // MOVIE
    MOVIE_NOT_FOUND(HttpStatus.NOT_FOUND, "MOV001", "해당 영화가 존재하지 않습니다."),
    MOVIE_LIKE_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "MOV002", "영화 좋아요/취소에 실패했습니다."),
    MOVIE_LIKE_INVALID_CURSOR(HttpStatus.BAD_REQUEST, "MOV003", "movieLikeId는 0보다 큰 값이어야 합니다."),
    MOVIE_EXISTS(HttpStatus.BAD_REQUEST, "MOV004", "해당 id를 가진 영화가 이미 존재합니다."),

    // FILM CREW
    ACTOR_NOT_FOUND(HttpStatus.NOT_FOUND, "CRW001", "해당 영화의 배우를 찾을 수 없습니다"),
    DIRECTOR_NOT_FOUND(HttpStatus.NOT_FOUND, "CRW002", "해당 영화의 감독을 찾을 수 없습니다"),

    // BOARD
    BOARD_USER_NOT_WRITER(HttpStatus.UNAUTHORIZED, "BOD001", "해당 게시물 작성자와 일치하지 않습니다."),
    BOARD_NOT_FOUND(HttpStatus.NOT_FOUND, "BOD002", "해당 게시물이 존재하지 않습니다."),
    BOARD_CREATE_FAILED(HttpStatus.BAD_REQUEST, "BOD003", "게시물 작성에 실패했습니다."),
    BOARD_UPDATE_FAILED(HttpStatus.BAD_REQUEST, "BOD004", "게시물 수정에 실패했습니다."),
    BOARD_MOVIE_RELATED_GET_FAILED(HttpStatus.BAD_REQUEST, "BOD005", "특정 영화에 대한 게시물들을 가져오는데에 실패했습니다."),
    BOARD_DELETE_FAILED(HttpStatus.BAD_REQUEST, "BOD006", "게시물 삭제에 실패했습니다."),
    BOARD_IS_DELETED(HttpStatus.BAD_REQUEST, "BOD007", "삭제된 게시물입니다."),
    BOARD_USER_ID_GET_FAILED(HttpStatus.BAD_REQUEST, "BOD009", "특정 사용자가 작성한 게시물들을 가져오는데에 실패했습니다."),
    BOARD_COUNT_FAIL(HttpStatus.BAD_REQUEST, "BOD010", "특정 사용자가 작성한 게시물 갯수를 가져오는데에 실패했습니다."),

    // BOARD_CONTENT
    INVALID_CONTENT_TYPE(HttpStatus.BAD_REQUEST, "BDC001","PHOTO, VIDEO값만 가능합니다"),
    BOARD_CONTENT_UPLOAD_FAILED(HttpStatus.BAD_REQUEST, "BDC002","게시글 콘텐츠 업로드에 실패했습니다."),
    BOARD_CONTENT_TOO_MANY(HttpStatus.BAD_REQUEST, "BDC003","영상 2개 혹은 사진 3개 제한을 초과했습니다"),
    BOARD_CONTENT_JSON_TRANSFERING_FAILED(HttpStatus.BAD_REQUEST, "BDC004","게시글 콘텐츠 타입 변환이 실패했습니다."),
    CONTENT_CONVERT_FAILED(HttpStatus.BAD_REQUEST, "BDC005","영상 및 사진 변환에 실패했습니다."),
    CONTENT_TO_CONVERT_NOT_FOUND(HttpStatus.NOT_FOUND, "BDC006","변환할 영상 및 사진이 없습니다."),
    LOCAL_CONTENT_TO_DELETE_NOT_FOUND(HttpStatus.NOT_FOUND, "BDC007","변환되어 로컬에 저장된 파일을 찾을 수 없어 삭제하지 못했습니다."),
    CONTENT_TO_UPLOAD_TO_S3_NOT_FOUND(HttpStatus.NOT_FOUND, "BDC008","S3에 업로드할 파일이 없습니다."),
    UPLOAD_TO_S3_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "BDC009","S3업로드를 실패했습니다."),
    CONTENT_UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "BDC010","업로드할 파일에 접근 권한이 없습니다."),
    S3_UPLOAD_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "BDC011","S3와 관련된 예외 (권한, 네트워크, 버킷 정책 등)이 발생했습니다."),
    CONTENT_IO_FAILED(HttpStatus.BAD_REQUEST, "BDC012", "파일 IO에서 예외가 발생했습니다"),
    UNKNOWN_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "BDC013", "파일 업로드에서 알 수 없는 예외가 발생했습니다."),
    EXTRACT_RUL_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "BDC014", "URL추출 중 오류가 발생했습니다."),
    MISSING_BOARD_CONTENT(HttpStatus.INTERNAL_SERVER_ERROR, "BDC015", "사진 혹은 영상이 적어도 하나는 있어야합니다."),

    // BOARD_COMMENT
    BOARD_COMMENT_CREATE_FAILED(HttpStatus.BAD_REQUEST, "BCM001", "댓글 작성에 실패했습니다."),
    BOARD_COMMENT_READ_FAILED(HttpStatus.BAD_REQUEST, "BCM002", "댓글 조회에 실패했습니다."),
    BOARD_COMMENT_NOT_FOUND(HttpStatus.BAD_REQUEST, "BCM003", "댓글이 존재하지 않습니다."),
    BOARD_COMMENT_USER_NOT_WRITER(HttpStatus.UNAUTHORIZED, "BOD004", "해당 댓글 작성자와 일치하지 않습니다."),
    BOARD_COMMENT_DELETE_FAILED(HttpStatus.BAD_REQUEST, "BOD005", "댓글 삭제에 실패했습니다."),

    // BOARD_LIKE
    BOARD_LIKE_DELETE_FAILED(HttpStatus.BAD_REQUEST, "BOL001", "게시물 좋아요 삭제에 실패했습니다."),
    BOARD_LIKE_CREATE_FAILED(HttpStatus.BAD_REQUEST, "BOL002", "게시물 좋아요 생성에 실패했습니다."),
    BOARD_LIKE_FAILED(HttpStatus.BAD_REQUEST, "BOL003", "게시물 좋아요 작업이 실패했습니다."),

    //LineReview
    LINEREVIEW_CREATE_FAILED(HttpStatus.BAD_REQUEST, "LR001", "한줄평 작성에 실패했습니다."),
    LINEREVIEW_INVALID_RATING(HttpStatus.BAD_REQUEST, "LR002", "평점 값은 0에서 5 사이입니다" ),
    LINEREVIEW_NOT_FOUND(HttpStatus.BAD_REQUEST, "LR003", "한줄평을 찾을 수 없습니다." ),
    LINEREVIEW_UPDATE_UNAUTHORIZED(HttpStatus.BAD_REQUEST, "LR004", "수정 권한이 없습니다." ),
    LINEREVIEW_UPDATE_FAILED(HttpStatus.BAD_REQUEST, "LR005", "한줄평 수정에 실패 했습니다." ),
    LINEREVIEW_CREATE_DUPLICATE(HttpStatus.BAD_REQUEST, "LR006", "한줄평은 계정당 한개만 작성가능합니다." ),
    LINEREVIEW_INVALID_SORTTYPE(HttpStatus.BAD_REQUEST, "LR007", "정렬은 Likes(좋아요순), Latest(최신순)만 가능합니다."),
    LINEREVIEW_INVALID_CURSOR1(HttpStatus.BAD_REQUEST, "LR008", "lastReviewId와 lastCreatedAt는 함께 제공되어야 합니다."),
    LINEREVIEW_INVALID_CURSOR2(HttpStatus.BAD_REQUEST, "LR009", "lastReviewId는 0보다 큰 값이어야 합니다."),
    LINEREVIEW_INVALID_CURSOR3(HttpStatus.BAD_REQUEST, "LR0010", "lastCreatedAt은 현재 시점 이전이어야 합니다."),
    LINEREVIEW_INVALID_SORT(HttpStatus.BAD_REQUEST, "LR0011", "유효하지 않은 정렬 방식입니다"),
    LINEREVIEW_GET_FAILED(HttpStatus.BAD_REQUEST, "LR0012", "한줄평 조회에 실패했습니다."),
    LINEREVIEW_GENDER_QUERY_FAILED(HttpStatus.BAD_REQUEST, "LR0013", "한줄평 성별에 따른 조회에 실패했습니다."),
    LINEREVIEW_RATING_QUERY_FAILED(HttpStatus.BAD_REQUEST, "LR0014", "한줄평 평점별 조회에 실패했습니다."),


    //LineReviewLike
    INVALID_PREFERENCE(HttpStatus.BAD_REQUEST, "LRL001","LIKE, DISLIKE값만 가능합니다"),
    LINEREVIEWLIKE_CREATE_FAILED(HttpStatus.BAD_REQUEST, "LRL002","한줄평 좋아요에 실패했습니다."),
    LINEREVIEWLIKE_CREATE_DUPLICATE(HttpStatus.BAD_REQUEST,"LRL003","좋아요나 싫어요는 한번만 가능합니다."),
    LINEREVIEW_COUNT_FAILED(HttpStatus.BAD_REQUEST, "LRL004", "좋아요 수 찾기 실패 했습니다."),
    LINEREVIEWLIKE_SELF_NOT_ALLOWED(HttpStatus.BAD_REQUEST, "LRL005", "자신의 한줄평에는 좋아요가 안됩니다." ),


    // Notification
    INVALID_NOTIFICATION_TYPE(HttpStatus.BAD_REQUEST, "NTF001","MOVIENEWBOARD(좋아요한 영화에 대해 새로운 게시글이 달린 경우), FOLLOWINGNEWBOARD(팔로우한 사용자가 새로운 게시글을 작성한 경우),MYBOARDNEWCOMMENT(내 게시글에 새로운 댓글이 달린 경우)만 가능합니다"),
    DTO_MAPPING_FAILED(HttpStatus.BAD_REQUEST, "NTF002","DTO 변환 과정에서 실패했습니다."),
    NOTIFICATION_NOT_FOUND(HttpStatus.BAD_REQUEST, "NTF003","존재하지 않는 알림입니다."),
    NOTIFICATION_UPDATE_FAILED(HttpStatus.BAD_REQUEST, "NTF004","알림 업데이트에 실패했습니다."),


    //Playlist
    PLAYLIST_NOT_FOUND(HttpStatus.NOT_FOUND, "PL001", "플레이리스트 조회에 실패했습니다."),
    PLAYLIST_MOVIE_NOT_FOUND(HttpStatus.NOT_FOUND, "PL002", "플레이리스트에 포함되어있는 영화 조회에 실패했습니다."),
    PLAYLIST_CREATE_FAILED(HttpStatus.BAD_REQUEST, "PL003", "플레이리스트에 추가하려는 영화 리스트 혹은 제목이 null입니다."),
    PLAYLIST_UPDATE_FAILED(HttpStatus.BAD_REQUEST, "PL004", "수정하려는 플레이리스트의 영화 리스트 혹은 제목이 null입니다."),

    // EMAIL
    EMAIL_SEND_FAILED(HttpStatus.BAD_REQUEST, "EML001", "이메일 전송에 실패했습니다."),
    USER_EMAIL_EMPTY(HttpStatus.BAD_REQUEST, "EML002", "해당 사용자의 이메일이 존재하지 않습니다."),

    // FOLLOW
    FOLLOW_SAME_USER(HttpStatus.BAD_REQUEST, "FLW001", "사용자 본인을 팔로우 할 수 없습니다."),

    ;

    private final HttpStatus httpStatus; // HTTP 상태 코드
    private final String code;          // 에러 코드
    private final String message;       // 에러 메시지

    ErrorCode(HttpStatus httpStatus, String code, String message) {
        this.httpStatus = httpStatus;
        this.code = code;
        this.message = message;
    }
}

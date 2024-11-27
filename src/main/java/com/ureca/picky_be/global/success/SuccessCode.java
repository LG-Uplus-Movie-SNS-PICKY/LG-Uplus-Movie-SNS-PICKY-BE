package com.ureca.picky_be.global.success;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SuccessCode {
    CREATE_MOVIE_SUCCESS(201, "영화 생성 완료"),
    DELETE_MOVIE_SUCCESS(200, "영화 삭제 완료"),
    GENERAL_SUCCESS(200, "요청이 성공적으로 처리되었습니다.");

    private final int status;    // HTTP 상태 코드
    private final String message; // 성공 메시지
}


///return CREATE_MOVIE_SUCCESS.getMessage();
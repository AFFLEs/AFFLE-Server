package com.AFFLE.server.global.status;

import com.AFFLE.server.global.BaseErrorCode;
import com.AFFLE.server.global.ErrorReasonDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorStatus implements BaseErrorCode {

    // 공통 에러
    _INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버 내부 오류입니다."),
    _BAD_REQUEST(HttpStatus.BAD_REQUEST, "잘못된 요청입니다."),
    _UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "인증이 필요합니다."),
    _FORBIDDEN(HttpStatus.FORBIDDEN, "접근이 금지되었습니다."),
    _NOT_FOUND(HttpStatus.NOT_FOUND, "요청한 리소스를 찾을 수 없습니다."),

    // Elder 관련
    ELDER_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 노인을 찾을 수 없습니다."),
    DUPLICATED_ELDER(HttpStatus.BAD_REQUEST, "이미 존재하는 노인입니다."),

    // Watch 관련
    WATCH_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 워치를 찾을 수 없습니다."),
    WATCH_ALREADY_REGISTERED(HttpStatus.BAD_REQUEST, "이미 등록된 워치입니다."),

    // MeterMan 관련
    METER_MAN_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 검침원을 찾을 수 없습니다."),
    METER_MAN_ALREADY_ASSIGNED(HttpStatus.BAD_REQUEST, "이미 배정된 검침원입니다.");

    private final HttpStatus httpStatus;
    private final String message;

    @Override
    public ErrorReasonDTO getReason() {
        return ErrorReasonDTO.builder()
                .isSuccess(false)
                .code(httpStatus.value())
                .message(message)
                .build();
    }

    @Override
    public ErrorReasonDTO getReasonHttpStatus() {
        return ErrorReasonDTO.builder()
                .isSuccess(false)
                .code(httpStatus.value())
                .message(message)
                .httpStatus(httpStatus)
                .build();
    }
}
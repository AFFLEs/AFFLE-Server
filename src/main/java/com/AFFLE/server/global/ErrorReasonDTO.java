package com.AFFLE.server.global;

import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@Builder
public class ErrorReasonDTO {
    private final boolean isSuccess;
    private final int code;
    private final String message;
    private HttpStatus httpStatus;

    public boolean getIsSuccess() {
        return isSuccess;
    }
}
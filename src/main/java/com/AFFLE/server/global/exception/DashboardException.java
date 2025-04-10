package com.AFFLE.server.global.exception;

import com.AFFLE.server.global.exception.GeneralException;
import com.AFFLE.server.global.status.ErrorStatus;

public class DashboardException extends GeneralException {
    public DashboardException(ErrorStatus status) {
        super(status);
    }
}

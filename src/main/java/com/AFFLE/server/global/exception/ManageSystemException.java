package com.AFFLE.server.global.exception;

import com.AFFLE.server.global.exception.GeneralException;
import com.AFFLE.server.global.status.ErrorStatus;

public class ManageSystemException extends GeneralException {
    public ManageSystemException(ErrorStatus status) {
        super(status);
    }
}

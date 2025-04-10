package com.AFFLE.server.global.exception;

import com.AFFLE.server.global.exception.GeneralException;
import com.AFFLE.server.global.status.ErrorStatus;

public class ManageElderlyException extends GeneralException {
    public ManageElderlyException(ErrorStatus status) {
        super(status);
    }
}

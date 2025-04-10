package com.AFFLE.server.global.exception;

import com.AFFLE.server.global.exception.GeneralException;
import com.AFFLE.server.global.status.ErrorStatus;

public class ManageOnSiteException extends GeneralException {
    public ManageOnSiteException(ErrorStatus status) {
        super(status);
    }
}

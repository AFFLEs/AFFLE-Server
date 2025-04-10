package com.AFFLE.server.global.exception;

import com.AFFLE.server.global.exception.GeneralException;
import com.AFFLE.server.global.status.ErrorStatus;

public class ManageHeatIllnessException extends GeneralException {
    public ManageHeatIllnessException(ErrorStatus status) {
        super(status);
    }
}

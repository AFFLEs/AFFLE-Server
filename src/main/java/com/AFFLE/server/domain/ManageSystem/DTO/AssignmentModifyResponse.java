package com.AFFLE.server.domain.ManageSystem.DTO;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class AssignmentModifyResponse {
    private String id;
    private String meterReader;
    private String elder;
}
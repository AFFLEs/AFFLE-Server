package com.AFFLE.server.domain.ManageSystem.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AssignmentModifyRequest {
    private String meter_reader; // ""이면 null
    private String elderly;      // ""이면 null
}
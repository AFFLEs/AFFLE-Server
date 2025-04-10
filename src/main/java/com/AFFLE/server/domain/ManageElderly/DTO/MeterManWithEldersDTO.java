package com.AFFLE.server.domain.ManageElderly.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class MeterManWithEldersDTO {
    private String meterManName;
    private String meterManLoc;
    private List<ElderlyResponseDTO> elderlyList;
}

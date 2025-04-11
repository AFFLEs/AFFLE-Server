package com.AFFLE.server.domain.ManageElderly.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class WatchStatusGroupedResponseDTO {
    private List<WatchWearingResponseDTO> wearing;
    private List<WatchStatusResponseDTO> notWearing;
}
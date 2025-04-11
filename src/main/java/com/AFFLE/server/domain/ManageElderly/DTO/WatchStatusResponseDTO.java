package com.AFFLE.server.domain.ManageElderly.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class WatchStatusResponseDTO {
    private Integer elderId;
    private String name;
    private String sex;
    private int age;
    private String lastWornDate; // 최근 착용일
    private String status;
}
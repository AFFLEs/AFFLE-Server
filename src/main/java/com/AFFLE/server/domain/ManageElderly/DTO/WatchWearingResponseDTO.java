package com.AFFLE.server.domain.ManageElderly.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class WatchWearingResponseDTO {
    private Integer elderId;
    private String name;
    private String sex;
    private int age;
    private String status; // 항상 "wearing"
}
package com.AFFLE.server.domain.ManageElderly.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class ElderlyResponseDTO {
    private String name;
    private String sex;
    private int age;
    private String address;
    private String recentVisitDate;
}
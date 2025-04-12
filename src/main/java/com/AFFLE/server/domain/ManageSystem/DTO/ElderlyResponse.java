package com.AFFLE.server.domain.ManageSystem.DTO;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ElderlyResponse {
    private Integer elderly_id;
    private String name;
    private String sex;
    private Integer age;
    private String recent_visit_date;
    private String region;
}

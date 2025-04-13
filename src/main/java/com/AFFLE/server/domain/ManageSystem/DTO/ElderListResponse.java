package com.AFFLE.server.domain.ManageSystem.DTO;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ElderListResponse {
    private Integer elderly_id;
    private String name;
    private String gender;
    private Integer age;
    private String recent_visit_date;
    private String next_visit_date;
    private String contact;
    private String category;
    private String region;
    private String address;
    private ManagerInfo manager;
}

package com.AFFLE.server.domain.ManageSystem.DTO;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class MeterManListResponse {
    private Integer manager_id;
    private String name;
    private String sex;
    private Integer age;
    private Integer work_status;
    private String work_region;
    private String work_time;
    private String contact;
    private String device;
    private String address;
    private List<ElderlyResponse> subject;
}

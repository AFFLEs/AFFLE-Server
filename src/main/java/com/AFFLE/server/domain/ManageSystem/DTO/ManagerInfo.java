package com.AFFLE.server.domain.ManageSystem.DTO;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ManagerInfo {
    private Integer manager_id;
    private String name;
    private String gender;
    private Integer age;
    private String work_region;
    private String contact;
}

package com.AFFLE.server.domain.ManageSystem.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MeterManRegisterRequest {
    private Boolean isAdmin;
    private String name;
    private Integer age;
    private String sex;
    private String address;
    private String contact;
    private String workTime;
    private String workRegion;
    private String device;
}

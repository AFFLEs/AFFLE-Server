package com.AFFLE.server.domain.ManageSystem.DTO;

import lombok.Getter;

@Getter
public class ElderRegisterRequest {
    private String name;
    private String sex; // "남성", "여성"
    private Integer age;
    private String phone;
    private Integer is_single; // 0 or 1
    private String address;
    private Integer manager_id; // MeterMan ID
}

package com.AFFLE.server.domain.ManageSystem.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class AssignmentListResponse {
    private String id;
    private String meter_reader;
    private String elder;
    private int health_status; // 0: 정상, 1: 경고, 2: 위험, 3: 미확인
    private String work_region;
    private int assign_status; // 0: 배정 완료, 1: 노인 없음, 2: 검침원 없음
}

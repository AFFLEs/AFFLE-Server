package com.AFFLE.server.domain.meterman.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class MeterMan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer metermanId;

    @Enumerated(EnumType.STRING)
    private Role role;

    // private Boolean isAdmin; // true면 총괄, false면 일반 검침원

    @Enumerated(EnumType.STRING)
    private WorkStatus workStatus;

    private String name;
    private String metermanLoc;

    public enum Role {
        MANAGER, // 총괄
        WORKER   // 일반 검침원
    }

    public enum WorkStatus {
        근무중, 휴게시간, 퇴근
    }
}
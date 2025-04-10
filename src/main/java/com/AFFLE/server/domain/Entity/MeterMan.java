package com.AFFLE.server.domain.Entity;

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

    @Column(columnDefinition = "TINYINT(1)")
    private Boolean isAdmin; // true면 총괄, false면 일반 검침원

    @Enumerated(EnumType.STRING)
    private WorkStatus workStatus;

    @Column(length = 50)
    private String name;

    @Column(length = 300)
    private String metermanLoc;

    public enum WorkStatus {
        근무중, 휴게시간, 퇴근
    }
}
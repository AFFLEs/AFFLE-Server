package com.AFFLE.server.domain.Entity;

import com.AFFLE.server.global.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class MeterMan extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer metermanId;

    @Column(columnDefinition = "TINYINT(1)")
    private Boolean isAdmin; // true: 총괄 관리자, false: 일반 검침원

    @Enumerated(EnumType.STRING)
    private WorkStatus workStatus; // 근무 상태

    // Personal Info
    @Column(length = 50)
    private String name;

    private Integer age;

    @Enumerated(EnumType.STRING)
    private Sex sex;

    @Column(length = 300)
    private String address;

    @Column(length = 100)
    private String contact;


    // Work Info
    @Column(length = 100)
    private String workTime;

    @Column(length = 100)
    private String workRegion; // 근무 지역

    @Column(length = 100)
    private String device;

    @Column(length = 300)
    private String meterManLoc; // 근무 위치


    public enum WorkStatus {
        근무중, 휴게시간, 퇴근
    }

    public enum Sex {
        남, 여
    }

    // Elder와 MeterMan의 관계 매핑
    @OneToMany(mappedBy = "meterMan", cascade = CascadeType.ALL)
    private List<ElderMeterMan> relations;
}

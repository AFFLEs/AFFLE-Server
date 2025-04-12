package com.AFFLE.server.domain.Entity;

import com.AFFLE.server.global.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(
        name = "elder_meter_man",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"elderId", "metermanId"})
        }
)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class ElderMeterMan extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 한 노인이 여러 검침원과 관계 맺을 수 있다고 가정
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "elderId", nullable = false)
    private Elder elder;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "metermanId", nullable = false)
    private MeterMan meterMan;

    @JsonFormat(pattern = "yyyy.MM.dd")
    private LocalDate recentVisitDate;

    @JsonFormat(pattern = "yyyy.MM.dd")
    private LocalDate nextVisitDate;
}

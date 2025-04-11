package com.AFFLE.server.domain.Entity;

import com.AFFLE.server.global.BaseEntity;
import com.AFFLE.server.domain.Entity.Elder;
import com.AFFLE.server.domain.Entity.MeterMan;
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
    @Id // 복합키 대신 단일키를 만들고 elderId + metermanId 조합에 unique를 걸었음
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
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

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
                @UniqueConstraint(columnNames = {"elderId"}) // 노인 당 한 명의 검침원이 배정되니까 elderId로만 Unique로 충분
        }
)
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class ElderMeterMan extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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

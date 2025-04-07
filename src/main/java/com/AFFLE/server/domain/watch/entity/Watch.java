package com.AFFLE.server.domain.watch.entity;

import com.AFFLE.server.domain.elder.entity.Elder;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Watch {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer watchId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "elderId")
    private Elder elder;

    private Integer heartRate;

    @Column(precision = 5, scale = 2)
    private BigDecimal skinTemp;

    @Column(length = 300)
    private String gps;

    @Column(columnDefinition = "TINYINT(1) DEFAULT 0")
    private Boolean isWorn; // true(1): 착용, false(0): 미착용
    private LocalDateTime curWorn;

    @Enumerated(EnumType.STRING)
    private HeatStatus heatStatus;

    private Boolean isFallen; // true(1): 낙상 발생, false(0): 이상 없음
    private Boolean isDanger; // true(1): 위험 지역에 있음, false(0): 안전 지역

    public enum HeatStatus {
        주의, 경고, 위험
    }
}
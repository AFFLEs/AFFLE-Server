package com.AFFLE.server.domain.Entity;

import com.AFFLE.server.global.BaseEntity;
import com.AFFLE.server.domain.Entity.Elder;
import com.AFFLE.server.domain.Entity.MeterMan;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class OnSiteAction extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer actionId;

    // 현장 조치 검침원
    @ManyToOne(fetch = FetchType.LAZY) // 한 명의 검침원이 여러 건의 조치를 할 수 있음
    @JoinColumn(name = "metermanId")
    private MeterMan meterMan;

    // 조치 대상 노인
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "elderId")
    private Elder elder;

    // 현재 조치 상태 저장
    @Enumerated(EnumType.STRING)
    private ActionStatus actionStatus;

    private LocalDateTime occurTime;
    private LocalDateTime completedTime;
    private LocalDateTime assignedTime;

    @Column(length = 300)
    private String cause;

    public enum ActionStatus {
        조치중, 조치완료
    }
}
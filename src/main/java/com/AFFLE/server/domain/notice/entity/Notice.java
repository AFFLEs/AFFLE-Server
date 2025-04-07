package com.AFFLE.server.domain.notice.entity;

import com.AFFLE.server.domain.meterman.entity.MeterMan;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;


@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Notice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer postId;

    @ManyToOne(fetch = FetchType.LAZY) // 여러 개의 공지를 하나의 검침원이 작성 가능
    // fetch = LAZY : 실제로 이 필드가 필요할 때만 검침원 정보를 불러옴
    @JoinColumn(name = "metermanId")
    private MeterMan meterMan;

    @Column(length = 50)
    private String title;
    private LocalDate date;
    private String content;

    @Column(length = 300)
    private String fileUrl;

    @Enumerated(EnumType.STRING)
    private Target target;

    public enum Target {
        검침원, 노인
    }
}
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
public class Elder extends BaseEntity {
    @Id // 기본키 설정
    @GeneratedValue(strategy = GenerationType.IDENTITY) // ID 자동 증가 설정
    private Integer elderId;

    @Column(length = 20)
    private String name;
    private int age;

    @Column(length = 20)
    private String phone;
    private Boolean isSingle; // 독거노인 여부

    @Enumerated(EnumType.STRING) // enum 값을 문자열로 저장한다는 표시
    private Sex sex;

    @Column(length = 200)
    private String address;
    public enum Sex {
        남, 여
    }

    // 노인 공지 알림
    @OneToMany(mappedBy = "elder", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Notification> notifications;

    @OneToOne(mappedBy = "elder", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private ElderMeterMan assignment;

    @OneToOne(mappedBy = "elder", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private Watch watch;


}
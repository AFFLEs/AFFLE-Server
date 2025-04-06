package com.AFFLE.server.domain.elder.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Elder {
    @Id // 기본키 설정
    @GeneratedValue(strategy = GenerationType.IDENTITY) // ID 자동 증가 설정
    private Integer elderId;

    private int age;

    @Enumerated(EnumType.STRING) // enum 값을 문자열로 저장한다는 표시
    private Sex sex;

    private String address;
    public enum Sex {
        남, 여
    }
}
package com.AFFLE.server.domain.ManageSystem.DTO;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class NoticeRegisterRequest {
    private String title;       // 제목 (null이면 노인 대상 알림)
    private String content;     // 내용
    private String author;      // 작성자
    private Integer target;     // null: 전체, 0: 검침원ID, 1: 노인ID
    private String attachment;  // 첨부파일 (null 가능)
}

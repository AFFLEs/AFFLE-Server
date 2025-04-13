package com.AFFLE.server.domain.ManageSystem.DTO;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class NoticeDetailResponse {
    private String id;            // 공지사항 ID
    private String title;         // 제목
    private String content;       // 내용
    private String author;        // 작성자
    private String date;          // 작성 날짜 (String으로 반환)
    private String attachment;    // 첨부 파일 경로 or 파일명
}

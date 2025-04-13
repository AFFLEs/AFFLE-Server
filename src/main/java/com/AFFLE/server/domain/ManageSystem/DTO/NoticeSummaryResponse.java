package com.AFFLE.server.domain.ManageSystem.DTO;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class NoticeSummaryResponse {
    private String id;
    private String title;
    private String author;
    private String date;
}

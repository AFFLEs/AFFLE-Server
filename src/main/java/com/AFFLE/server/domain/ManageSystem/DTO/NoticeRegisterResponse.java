package com.AFFLE.server.domain.ManageSystem.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class NoticeRegisterResponse {
    private Integer id;
    private Integer target;
}

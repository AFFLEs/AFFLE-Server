package com.AFFLE.server.domain.ManageElderly.DTO;

import lombok.NoArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@NoArgsConstructor
public class WatchStatusNotifyRequestDTO {
    private List<Integer> elderIds;
}
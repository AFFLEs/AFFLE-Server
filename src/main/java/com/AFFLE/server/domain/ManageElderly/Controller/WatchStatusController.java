package com.AFFLE.server.domain.ManageElderly.Controller;

import com.AFFLE.server.domain.ManageElderly.DTO.WatchStatusGroupedResponseDTO;
import com.AFFLE.server.domain.ManageElderly.DTO.WatchStatusNotifyRequestDTO;
import com.AFFLE.server.domain.ManageElderly.Service.WatchStatusService;
import com.AFFLE.server.global.common.ApiResponse;
import com.AFFLE.server.global.exception.ManageElderlyException;
import com.AFFLE.server.global.status.ErrorStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/elderly-management/watch")
public class WatchStatusController {

    private final WatchStatusService watchStatusService;

    @GetMapping
    public ApiResponse<List<?>> getByStatus(@RequestParam("status") String status) {
        return switch (status) {
            case "wearing" -> ApiResponse.onSuccess(watchStatusService.getWatchesByStatus(true));
            case "not_wearing" -> ApiResponse.onSuccess(watchStatusService.getWatchesByStatus(false));
            default -> throw new ManageElderlyException(ErrorStatus.WATCH_STATUS_INVALID);
        };
    }

    @GetMapping(params = "search")
    public ApiResponse<WatchStatusGroupedResponseDTO> searchByName(@RequestParam String search) {
        return ApiResponse.onSuccess(watchStatusService.searchByName(search));
    }

    @PostMapping("/notify")
    public ApiResponse<String> sendNotification(@RequestBody WatchStatusNotifyRequestDTO request) {
        int count = watchStatusService.notifyRecommendation(request.getElderIds());
        return ApiResponse.onSuccess("총 " + count + "명의 노인에게 착용 권장 알림이 전송되었습니다.");
    }
}
package com.AFFLE.server.domain.ManageSystem.Controller;

import com.AFFLE.server.domain.ManageSystem.DTO.NoticeDetailResponse;
import com.AFFLE.server.domain.ManageSystem.DTO.NoticeRegisterRequest;
import com.AFFLE.server.domain.ManageSystem.DTO.NoticeRegisterResponse;
import com.AFFLE.server.domain.ManageSystem.DTO.NoticeSummaryResponse;
import com.AFFLE.server.domain.ManageSystem.Service.NoticeService;
import com.AFFLE.server.global.common.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/notices")
@RequiredArgsConstructor
public class NoticeController {

    private final NoticeService noticeService;

    @GetMapping("")
    public ApiResponse<List<NoticeSummaryResponse>> getNoticesForMeterMan(
            @RequestHeader("Authorization") String authHeader,
            @RequestParam("meter") Integer managerId
    ) {
        return ApiResponse.onSuccess(noticeService.getNoticesForMeterReader(managerId));
    }

    @GetMapping("/{notice_id}")
    public ApiResponse<NoticeDetailResponse> getNoticeDetail(
            @PathVariable("notice_id") Integer noticeId,
            @RequestHeader("Authorization") String token
    ) {
        NoticeDetailResponse detail = noticeService.getNoticeDetail(noticeId);
        return ApiResponse.onSuccess(detail);
    }

    @DeleteMapping("/delete/{notice_id}")
    public ApiResponse<String> deleteNotice(
            @PathVariable("notice_id") Integer noticeId,
            @RequestParam("meter") Integer meterId) {
        noticeService.deleteNoticeByAuthor(noticeId, meterId);
        return ApiResponse.onSuccess("공지사항이 삭제되었습니다.");
    }


    @PostMapping("/register")
    public ApiResponse<NoticeRegisterResponse> registerNotice(
            @RequestBody NoticeRegisterRequest request,
            @RequestHeader("Authorization") String token
    ) {
        NoticeRegisterResponse response = noticeService.registerNoticeOrNotification(request);
        return ApiResponse.onSuccess(response);
    }


}

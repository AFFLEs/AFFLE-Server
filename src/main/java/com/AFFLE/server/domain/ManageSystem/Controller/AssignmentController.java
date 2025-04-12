package com.AFFLE.server.domain.ManageSystem.Controller;

import com.AFFLE.server.domain.ManageSystem.DTO.AssignmentListResponse;
import com.AFFLE.server.domain.ManageSystem.DTO.AssignmentModifyRequest;
import com.AFFLE.server.domain.ManageSystem.DTO.AssignmentModifyResponse;
import com.AFFLE.server.domain.ManageSystem.Service.AssignmentService;
import com.AFFLE.server.domain.ManageSystem.Service.ElderService;
import com.AFFLE.server.global.common.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin/assign")
@RequiredArgsConstructor
public class AssignmentController {

    private final AssignmentService assignService;

    @GetMapping("/view")
    public ApiResponse<List<AssignmentListResponse>> getAssignmentList(
            @RequestHeader("Authorization") String authHeader) {
        return ApiResponse.onSuccess(assignService.getAssignmentList());
    }

    @GetMapping("/search")
    public ApiResponse<List<AssignmentListResponse>> searchByKeyword(
            @RequestParam(required = false) String meter,
            @RequestParam(required = false) String elder,
            @RequestHeader("Authorization") String token) {

        if (meter != null) {
            return ApiResponse.onSuccess(assignService.searchByMeterReaderName(meter));
        } else if (elder != null) {
            return ApiResponse.onSuccess(assignService.searchByElderName(elder));
        } else {
            return ApiResponse.onSuccess(assignService.getAssignmentList());
        }
    }

    @PutMapping("/modify/{assign_id}")
    public ApiResponse<Map<String, String>> modifyAssignment(
            @PathVariable("assign_id") Long assignId,
            @RequestBody AssignmentModifyRequest request,
            @RequestHeader("Authorization") String token
    ) {
        AssignmentModifyResponse response = assignService.modifyAssignment(assignId, request);
        return ApiResponse.onSuccess(Map.of(
                "id", response.getId(),
                "meter_reader", response.getMeterReader(),
                "elderly", response.getElder()
        ));
    }

}

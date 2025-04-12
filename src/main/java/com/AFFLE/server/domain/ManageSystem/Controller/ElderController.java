package com.AFFLE.server.domain.ManageSystem.Controller;

import com.AFFLE.server.domain.ManageSystem.DTO.ElderListResponse;
import com.AFFLE.server.domain.ManageSystem.DTO.ElderRegisterRequest;
import com.AFFLE.server.domain.ManageSystem.DTO.ElderRegisterResponse;
import com.AFFLE.server.domain.ManageSystem.Service.ElderService;
import com.AFFLE.server.global.common.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/elderly")
@RequiredArgsConstructor
public class ElderController {

    private final ElderService elderService;

    @PostMapping("/register")
    public ApiResponse<ElderRegisterResponse> registerElder(
            @RequestBody ElderRegisterRequest request,
            @RequestHeader("Authorization") String token
    ) {
        ElderRegisterResponse response = elderService.registerElder(request);
        return ApiResponse.onSuccess(response);
    }

    @DeleteMapping("/delete/{elderly_id}")
    public ResponseEntity<ApiResponse<String>> deleteElder(
            @PathVariable("elderly_id") Integer elderlyId,
            @RequestHeader("Authorization") String authHeader) {

        elderService.deleteElder(elderlyId);

        return ResponseEntity.ok(ApiResponse.onSuccess("노인이 삭제되었습니다."));
    }

    @GetMapping("/view")
    public ApiResponse<List<ElderListResponse>> getElderList(
            @RequestHeader("Authorization") String token) {
        List<ElderListResponse> response = elderService.getAllElders();
        return ApiResponse.onSuccess(response);
    }

}

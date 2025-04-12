package com.AFFLE.server.domain.ManageSystem.Controller;

import com.AFFLE.server.domain.ManageSystem.DTO.MeterManListResponse;
import com.AFFLE.server.domain.ManageSystem.DTO.MeterManRegisterRequest;
import com.AFFLE.server.domain.ManageSystem.DTO.MeterManRegisterResponse;
import com.AFFLE.server.domain.ManageSystem.Service.MeterManService;
import com.AFFLE.server.global.common.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/meter-readers")
@RequiredArgsConstructor
public class MeterManController {

    private final MeterManService meterManService;

    @PostMapping("/register")
    public ApiResponse<MeterManRegisterResponse> register(@RequestBody MeterManRegisterRequest request) {
        MeterManRegisterResponse response = meterManService.register(request);
        return ApiResponse.onSuccess(response);
    }

    @DeleteMapping("/delete/{manager_id}")
    public ApiResponse<String> deleteMeterMan(@PathVariable("manager_id") Integer managerId) {
        meterManService.deleteMeterMan(managerId);
        return ApiResponse.onSuccess("검침원이 삭제되었습니다.");
    }

    @GetMapping("/view")
    public ApiResponse<List<MeterManListResponse>> getAllMeterMen(@RequestHeader("Authorization") String authorization) {
        List<MeterManListResponse> list = meterManService.getAllMeterMen();
        return ApiResponse.onSuccess(list);
    }
}

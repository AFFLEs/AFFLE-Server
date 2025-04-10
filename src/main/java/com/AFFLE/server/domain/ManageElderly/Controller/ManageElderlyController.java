package com.AFFLE.server.domain.ManageElderly.Controller;

import com.AFFLE.server.domain.ManageElderly.DTO.MeterManWithEldersDTO;
import com.AFFLE.server.domain.ManageElderly.Service.ManageElderlyService;
import com.AFFLE.server.global.common.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/elderly-management")
public class ManageElderlyController {

    private final ManageElderlyService manageElderlyService;

    @GetMapping("/all")
    public ApiResponse<List<MeterManWithEldersDTO>> getAllElderlyWithMeterMan(@RequestParam(required = false) String search) {
        List<MeterManWithEldersDTO> result = manageElderlyService.getAllElderlyWithMeterMan(search);
        return ApiResponse.onSuccess(result);
    }
}
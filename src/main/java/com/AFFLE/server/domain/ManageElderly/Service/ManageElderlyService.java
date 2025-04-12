package com.AFFLE.server.domain.ManageElderly.Service;

import com.AFFLE.server.domain.Entity.ElderMeterMan;
import com.AFFLE.server.domain.ManageElderly.DTO.ElderlyResponseDTO;
import com.AFFLE.server.domain.ManageElderly.DTO.MeterManWithEldersDTO;
import com.AFFLE.server.domain.ManageElderly.Repository.ElderMeterManRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ManageElderlyService {

    private final ElderMeterManRepository elderMeterManRepository;

    public List<MeterManWithEldersDTO> getAllElderlyWithMeterMan(String search) {
        List<ElderMeterMan> data = (search == null || search.isEmpty()) ?
                elderMeterManRepository.findAll() :
                elderMeterManRepository.findByMeterMan_NameContaining(search);

        // MeterMan 기준으로 Elderly 리스트 묶기
        Map<String, List<ElderMeterMan>> grouped = data.stream()
                .collect(Collectors.groupingBy(em -> em.getMeterMan().getName() + "||" + em.getMeterMan().getMeterManLoc()));

        List<MeterManWithEldersDTO> result = new ArrayList<>();

        for (Map.Entry<String, List<ElderMeterMan>> entry : grouped.entrySet()) {
            String[] meterManInfo = entry.getKey().split("\\|\\|");
            String meterManName = meterManInfo[0];
            String meterManLoc = meterManInfo[1];

            List<ElderlyResponseDTO> elderlyList = entry.getValue().stream()
                    .map(em -> ElderlyResponseDTO.builder()
                            .name(em.getElder().getName())
                            .sex(em.getElder().getSex().name())
                            .age(em.getElder().getAge())
                            .address(em.getElder().getAddress())
                            .recentVisitDate(em.getRecentVisitDate().toString())
                            .build())
                    .collect(Collectors.toList());

            result.add(MeterManWithEldersDTO.builder()
                    .meterManName(meterManName)
                    .meterManLoc(meterManLoc)
                    .elderlyList(elderlyList)
                    .build());
        }

        return result;
    }
}
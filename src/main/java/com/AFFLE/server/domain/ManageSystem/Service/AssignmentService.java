package com.AFFLE.server.domain.ManageSystem.Service;

import com.AFFLE.server.domain.Entity.Elder;
import com.AFFLE.server.domain.Entity.ElderMeterMan;
import com.AFFLE.server.domain.Entity.MeterMan;
import com.AFFLE.server.domain.ManageElderly.Repository.ElderMeterManRepository;
import com.AFFLE.server.domain.ManageSystem.DTO.AssignmentListResponse;
import com.AFFLE.server.domain.ManageSystem.DTO.AssignmentModifyRequest;
import com.AFFLE.server.domain.ManageSystem.DTO.AssignmentModifyResponse;
import com.AFFLE.server.domain.ManageSystem.Repository.ElderRepository;
import com.AFFLE.server.domain.ManageSystem.Repository.MeterManRepository;
import com.AFFLE.server.global.exception.ManageSystemException;
import com.AFFLE.server.global.status.ErrorStatus;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AssignmentService {

    private final ElderMeterManRepository elderMeterManRepository;
    private final ElderRepository elderRepository;
    private final MeterManRepository meterManRepository;

    public List<AssignmentListResponse> getAssignmentList() {
        List<ElderMeterMan> relations = elderMeterManRepository.findAll();
        List<Elder> allElders = elderRepository.findAll();
        List<MeterMan> allMeterMen = meterManRepository.findAll();

        Set<Integer> assignedElderIds = relations.stream()
                .map(r -> r.getElder().getElderId())
                .collect(Collectors.toSet());

        Set<Integer> assignedMeterManIds = relations.stream()
                .map(r -> r.getMeterMan().getMetermanId())
                .collect(Collectors.toSet());

        List<AssignmentListResponse> result = new ArrayList<>();

        // 1. 배정된 관계
        for (ElderMeterMan r : relations) {
            Elder elder = r.getElder();
            MeterMan meterMan = r.getMeterMan();

            result.add(AssignmentListResponse.builder()
                    .id(String.valueOf(r.getId()))
                    .elder(elder.getName())
                    .meter_reader(meterMan.getName())
                    .work_region(meterMan.getWorkRegion())
                    .health_status(0) // Dummy
                    .assign_status(0) // 배정 완료
                    .build());
        }

        // 2. 배정되지 않은 Elder
        for (Elder e : allElders) {
            if (!assignedElderIds.contains(e.getElderId())) {
                result.add(AssignmentListResponse.builder()
                        .id("elder-" + e.getElderId())
                        .elder(e.getName())
                        .meter_reader(null)
                        .work_region("-")
                        .health_status(0) // Dummy
                        .assign_status(2) // 검침원 없음
                        .build());
            }
        }

        // 3. 배정되지 않은 MeterMan
        for (MeterMan m : allMeterMen) {
            if (!assignedMeterManIds.contains(m.getMetermanId())) {
                result.add(AssignmentListResponse.builder()
                        .id("meter-" + m.getMetermanId())
                        .elder(null)
                        .meter_reader(m.getName())
                        .work_region(m.getWorkRegion())
                        .health_status(0) // Dummy
                        .assign_status(1) // 노인 없음
                        .build());
            }
        }

        return result;
    }

    public List<AssignmentListResponse> searchByMeterReaderName(String nameKeyword) {
        // 검침원 전체 조회
        List<MeterMan> meterMen = meterManRepository.findByNameContaining(nameKeyword);

        if (meterMen.isEmpty()) {
            throw new ManageSystemException(ErrorStatus.METER_MAN_NOT_FOUND);
        }

        List<AssignmentListResponse> results = new ArrayList<>();

        for (MeterMan meterMan : meterMen) {
            List<ElderMeterMan> assignments = elderMeterManRepository.findByMeterMan(meterMan);

            if (assignments.isEmpty()) {
                // 배정된 노인이 없는 경우
                results.add(AssignmentListResponse.builder()
                        .id("-")
                        .elder("-")
                        .meter_reader(meterMan.getName())
                        .work_region(meterMan.getWorkRegion())
                        .health_status(0)
                        .assign_status(1) // 노인 없음
                        .build());
            } else {
                // 배정된 노인이 있는 경우
                for (ElderMeterMan r : assignments) {
                    Elder elder = r.getElder();

                    results.add(AssignmentListResponse.builder()
                            .id(String.valueOf(r.getId()))
                            .elder(elder != null ? elder.getName() : "-")
                            .meter_reader(meterMan.getName())
                            .work_region(meterMan.getWorkRegion())
                            .health_status(0)
                            .assign_status((elder != null) ? 0 : 1) // 0: 배정 완료, 1: 노인 없음
                            .build());
                }
            }
        }

        return results;
    }

    public List<AssignmentListResponse> searchByElderName(String nameKeyword) {
        // Elder 전체 조회
        List<Elder> elders = elderRepository.findByNameContaining(nameKeyword);

        if (elders.isEmpty()) {
            throw new ManageSystemException(ErrorStatus.ELDER_NOT_FOUND);
        }

        List<AssignmentListResponse> results = new ArrayList<>();

        for (Elder elder : elders) {
            List<ElderMeterMan> assignments = elderMeterManRepository.findByElder(elder);

            if (assignments.isEmpty()) {
                // 배정된 검침원이 없는 경우
                results.add(AssignmentListResponse.builder()
                        .id("-")
                        .elder(elder.getName())
                        .meter_reader("-")
                        .work_region("-")
                        .health_status(0)
                        .assign_status(2) // 검침원 없음
                        .build());
            } else {
                for (ElderMeterMan r : assignments) {
                    MeterMan meterMan = r.getMeterMan();

                    results.add(AssignmentListResponse.builder()
                            .id(String.valueOf(r.getId()))
                            .elder(elder.getName())
                            .meter_reader(meterMan != null ? meterMan.getName() : "-")
                            .work_region(meterMan != null ? meterMan.getWorkRegion() : "-")
                            .health_status(0)
                            .assign_status((meterMan != null) ? 0 : 2)
                            .build());
                }
            }
        }

        return results;
    }


    @Transactional
    public AssignmentModifyResponse modifyAssignment(Long assignId, AssignmentModifyRequest request) {
        ElderMeterMan relation = elderMeterManRepository.findById(assignId)
                .orElseThrow(() -> new ManageSystemException(ErrorStatus.RELATION_NOT_FOUND));

        // 검침원 변경
        if (request.getMeter_reader() != null && !request.getMeter_reader().isEmpty()) {
            MeterMan meterMan = meterManRepository.findByName(request.getMeter_reader())
                    .orElseThrow(() -> new ManageSystemException(ErrorStatus.METER_MAN_NOT_FOUND));
            relation.setMeterMan(meterMan);
        } else {
            relation.setMeterMan(null); // 미배정
        }

        // 노인 변경
        if (request.getElderly() != null && !request.getElderly().isEmpty()) {
            Elder elder = elderRepository.findByName(request.getElderly())
                    .orElseThrow(() -> new ManageSystemException(ErrorStatus.ELDER_NOT_FOUND));
            relation.setElder(elder);
        } else {
            relation.setElder(null); // 미배정
        }

        elderMeterManRepository.save(relation);

        return AssignmentModifyResponse.builder()
                .id(String.valueOf(relation.getId()))
                .meterReader(relation.getMeterMan() != null ? relation.getMeterMan().getName() : "-")
                .elder(relation.getElder() != null ? relation.getElder().getName() : "-")
                .build();
    }




}

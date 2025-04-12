package com.AFFLE.server.domain.ManageSystem.Service;

import com.AFFLE.server.domain.Entity.Elder;
import com.AFFLE.server.domain.Entity.ElderMeterMan;
import com.AFFLE.server.domain.Entity.MeterMan;
import com.AFFLE.server.domain.ManageSystem.DTO.ElderListResponse;
import com.AFFLE.server.domain.ManageSystem.DTO.ElderRegisterRequest;
import com.AFFLE.server.domain.ManageSystem.DTO.ElderRegisterResponse;
import com.AFFLE.server.domain.ManageSystem.DTO.ManagerInfo;
import com.AFFLE.server.domain.ManageSystem.Repository.ElderRepository;
import com.AFFLE.server.domain.ManageSystem.Repository.MeterManRepository;
import com.AFFLE.server.domain.ManageElderly.Repository.ElderMeterManRepository;
import com.AFFLE.server.global.exception.ManageSystemException;
import com.AFFLE.server.global.status.ErrorStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ElderService {

    private final ElderRepository elderRepository;
    private final MeterManRepository meterManRepository;
    private final ElderMeterManRepository elderMeterManRepository;

    public ElderRegisterResponse registerElder(ElderRegisterRequest request) {
        if (request.getName() == null || request.getPhone() == null || request.getSex() == null) {
            throw new ManageSystemException(ErrorStatus.NO_ESSENTIAL_INFO);
        }

        if (elderRepository.existsByPhone(request.getPhone())) {
            throw new ManageSystemException(ErrorStatus.DUPLICATED_CONTACT);
        }

        MeterMan meterMan = meterManRepository.findById(request.getManager_id())
                .orElseThrow(() -> new ManageSystemException(ErrorStatus.METER_MAN_NOT_FOUND));

        Elder elder = Elder.builder()
                .name(request.getName())
                .sex(Elder.Sex.valueOf(request.getSex().substring(0, 1)))
                .age(request.getAge())
                .phone(request.getPhone())
                .isSingle(request.getIs_single() == 1)
                .address(request.getAddress())
                .build();

        Elder saved = elderRepository.save(elder);

        ElderMeterMan relation = ElderMeterMan.builder()
                .elder(saved)
                .meterMan(meterMan)
                .recentVisitDate(null)
                .nextVisitDate(null)
                .build();

        elderMeterManRepository.save(relation);

        return new ElderRegisterResponse(saved.getElderId(), saved.getName());
    }

    public void deleteElder(Integer elderlyId) {
        Elder elder = elderRepository.findById(elderlyId)
                .orElseThrow(() -> new ManageSystemException(ErrorStatus.ELDER_NOT_FOUND));

        elderRepository.delete(elder);
    }

    public List<ElderListResponse> getAllElders() {
        List<Elder> elders = elderRepository.findAll();

        return elders.stream().map(elder -> {
            ElderMeterMan relation = elder.getAssignment();

            MeterMan manager = null;
            if (relation != null) {
                manager = relation.getMeterMan();
            }

            // 주소에서 Region 추출
            String region = null;
            String[] parts = elder.getAddress().split(" ");
            if (parts.length >= 3) {
                region = parts[1] + " " + parts[2];
            }

            return ElderListResponse.builder()
                    .elderly_id(elder.getElderId())
                    .name(elder.getName())
                    .gender(elder.getSex().name().equals("남") ? "남성" : "여성")
                    .age(elder.getAge())
                    .contact(elder.getPhone())
                    .category(elder.getIsSingle() ? "독거 노인" : "일반 노인")
                    .recent_visit_date(relation != null && relation.getRecentVisitDate() != null ? relation.getRecentVisitDate().toString() : null)
                    .next_visit_date(relation != null && relation.getNextVisitDate() != null ? relation.getNextVisitDate().toString() : null)
                    .region(region)
                    .address(elder.getAddress())
                    .manager(manager != null ? ManagerInfo.builder()
                            .manager_id(manager.getMetermanId())
                            .name(manager.getName())
                            .gender(manager.getSex().name().equals("남") ? "남성" : "여성")
                            .age(manager.getAge())
                            .work_region(manager.getWorkRegion())
                            .contact(manager.getContact())
                            .build() : null)
                    .build();
        }).toList();
    }


}

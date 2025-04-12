package com.AFFLE.server.domain.ManageSystem.Service;

import com.AFFLE.server.domain.Entity.Elder;
import com.AFFLE.server.domain.Entity.MeterMan;
import com.AFFLE.server.domain.ManageSystem.DTO.ElderlyResponse;
import com.AFFLE.server.domain.ManageSystem.DTO.MeterManListResponse;
import com.AFFLE.server.domain.ManageSystem.DTO.MeterManRegisterRequest;
import com.AFFLE.server.domain.ManageSystem.DTO.MeterManRegisterResponse;
import com.AFFLE.server.domain.ManageSystem.Repository.MeterManRepository;
import com.AFFLE.server.global.exception.ManageSystemException;
import com.AFFLE.server.global.status.ErrorStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MeterManService {

    private final MeterManRepository meterManRepository;

    public MeterManRegisterResponse register(MeterManRegisterRequest request) {
        if (request.getName() == null || request.getContact() == null) {
            throw new ManageSystemException(ErrorStatus.NO_ESSENTIAL_INFO);
        }

        if (meterManRepository.existsByContact(request.getContact())) {
            throw new ManageSystemException(ErrorStatus.DUPLICATED_CONTACT);
        }

        MeterMan meterMan = MeterMan.builder()
                .isAdmin(request.getIsAdmin())
                .name(request.getName())
                .sex(MeterMan.Sex.valueOf(request.getSex()))
                .age(request.getAge())
                .workRegion(request.getWorkRegion())
                .workTime(request.getWorkTime())
                .contact(request.getContact())
                .device(request.getDevice())
                .address(request.getAddress())
                .workStatus(MeterMan.WorkStatus.근무중) // Dummy Work Status -> 추후 삭제 후 근무 시간에 따라 주기적으로 업데이트 예정
                .build();

        MeterMan saved = meterManRepository.save(meterMan);

        return new MeterManRegisterResponse(saved.getMetermanId(), saved.getName());
    }

    public void deleteMeterMan(Integer metermanId) {
        boolean exists = meterManRepository.existsById(metermanId);
        if (!exists) {
            throw new ManageSystemException(ErrorStatus.METER_MAN_NOT_FOUND);
        }

        meterManRepository.deleteById(metermanId);
    }

    public List<MeterManListResponse> getAllMeterMen() {
        List<MeterMan> all = meterManRepository.findAll();

        return all.stream().map(meterMan -> MeterManListResponse.builder()
                .manager_id(meterMan.getMetermanId())
                .name(meterMan.getName())
                .sex(meterMan.getSex() != null ? meterMan.getSex().name() : null)
                .age(meterMan.getAge())
                .work_status(
                        meterMan.getWorkStatus() != null
                        ? meterMan.getWorkStatus().ordinal()
                        : MeterMan.WorkStatus.근무중.ordinal()
                )
                .work_region(meterMan.getWorkRegion())
                .work_time(meterMan.getWorkTime())
                .contact(meterMan.getContact())
                .device(meterMan.getDevice())
                .address(meterMan.getAddress())
                .subject(
                        meterMan.getRelations().stream()
                                .map(relation -> {
                                    Elder elder = relation.getElder();
                                    String address = elder.getAddress();
                                    String region = null;
                                    if (address != null) {
                                        String[] parts = address.split(" ");
                                        if (parts.length >= 3) {
                                            region = parts[1] + " " + parts[2];
                                        }
                                    }

                                    return ElderlyResponse.builder()
                                            .elderly_id(elder.getElderId())
                                            .name(elder.getName())
                                            .sex(elder.getSex().name())
                                            .age(elder.getAge())
                                            .recent_visit_date(
                                                    relation.getRecentVisitDate() != null
                                                            ? relation.getRecentVisitDate().toString()
                                                            : null
                                            )
                                            .region(region)
                                            .build();
                                }).toList()
                )
                .build()
        ).toList();
    }
}

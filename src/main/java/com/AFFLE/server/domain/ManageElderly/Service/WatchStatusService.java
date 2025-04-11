package com.AFFLE.server.domain.ManageElderly.Service;

import com.AFFLE.server.domain.Entity.Elder;
import com.AFFLE.server.domain.Entity.Notification;
import com.AFFLE.server.domain.Entity.Watch;
import com.AFFLE.server.domain.ManageElderly.DTO.WatchStatusGroupedResponseDTO;
import com.AFFLE.server.domain.ManageElderly.DTO.WatchStatusNotifyRequestDTO;
import com.AFFLE.server.domain.ManageElderly.DTO.WatchStatusResponseDTO;
import com.AFFLE.server.domain.ManageElderly.DTO.WatchWearingResponseDTO;
import com.AFFLE.server.domain.ManageElderly.Repository.NotificationRepository;
import com.AFFLE.server.domain.ManageElderly.Repository.WatchRepository;
import com.AFFLE.server.global.exception.ManageElderlyException;
import com.AFFLE.server.global.status.ErrorStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WatchStatusService {

    private final WatchRepository watchRepository;
    private final NotificationRepository notificationRepository;

    public List<?> getWatchesByStatus(boolean isWorn) {
        List<Watch> watches = watchRepository.findByIsWorn(isWorn);

        if (isWorn) {
            return watches.stream()
                    .map(watch -> {
                        Elder elder = watch.getElder();
                        return WatchWearingResponseDTO.builder()
                                .elderId(elder.getElderId())
                                .name(elder.getName())
                                .sex(elder.getSex().name())
                                .age(elder.getAge())
                                .status("wearing")
                                .build();
                    })
                    .collect(Collectors.toList());
        } else {
            return watches.stream()
                    .map(this::toResponseDTO)
                    .collect(Collectors.toList());
        }
    }

    public WatchStatusGroupedResponseDTO searchByName(String keyword) {
        List<Watch> results = watchRepository.findByElderNameContaining(keyword);

        List<WatchWearingResponseDTO> wearing = results.stream()
                .filter(Watch::getIsWorn)
                .map(watch -> {
                    Elder elder = watch.getElder();
                    return WatchWearingResponseDTO.builder()
                            .elderId(elder.getElderId())
                            .name(elder.getName())
                            .sex(elder.getSex().name())
                            .age(elder.getAge())
                            .status("wearing")
                            .build();
                })
                .collect(Collectors.toList());

        List<WatchStatusResponseDTO> notWearing = results.stream()
                .filter(watch -> !watch.getIsWorn())
                .map(this::toResponseDTO)
                .collect(Collectors.toList());

        return WatchStatusGroupedResponseDTO.builder()
                .wearing(wearing)
                .notWearing(notWearing)
                .build();
    }

    public int notifyRecommendation(List<Integer> elderIds) {
        if (elderIds == null || elderIds.isEmpty()) {
            throw new ManageElderlyException(ErrorStatus.WATCH_NOTIFY_TARGET_EMPTY);
        }

        for (Integer elderId : elderIds) {
            Watch watch = watchRepository.findById(elderId)
                    .orElseThrow(() -> new ManageElderlyException(ErrorStatus.ELDER_NOT_FOUND));

            Elder elder = watch.getElder();
            String message = "워치를 착용해주세요!";
            // System.out.println("\uD83D\uDD14 워치 착용 권장 알림 전송 → 대상 노인 ID: " + elderId + " / 메시지: '" + message + "'");

            Notification log = Notification.builder()
                    .elder(elder)
                    .message(message)
                    .sentAt(LocalDateTime.now())
                    .build();

            notificationRepository.save(log);
        }

        return elderIds.size();
    }

    private WatchStatusResponseDTO toResponseDTO(Watch watch) {
        Elder elder = watch.getElder();
        return WatchStatusResponseDTO.builder()
                .elderId(elder.getElderId())
                .name(elder.getName())
                .sex(elder.getSex().name())
                .age(elder.getAge())
                .lastWornDate(formatLastWornDate(watch.getCurWorn()))
                .status(watch.getIsWorn() ? "wearing" : "not_wearing")
                .build();
    }

    private String formatLastWornDate(LocalDateTime curWorn) {
        if (curWorn == null) return "기록 없음";
        long days = Duration.between(curWorn.toLocalDate().atStartOfDay(), LocalDate.now().atStartOfDay()).toDays();
        return days == 0 ? "오늘" : days + "일 전";
    }
}

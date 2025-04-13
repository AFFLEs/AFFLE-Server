package com.AFFLE.server.domain.ManageSystem.Service;

import com.AFFLE.server.domain.Entity.Elder;
import com.AFFLE.server.domain.Entity.Notice;
import com.AFFLE.server.domain.Entity.Notification;
import com.AFFLE.server.domain.ManageElderly.Repository.NotificationRepository;
import com.AFFLE.server.domain.ManageSystem.DTO.NoticeDetailResponse;
import com.AFFLE.server.domain.ManageSystem.DTO.NoticeRegisterRequest;
import com.AFFLE.server.domain.ManageSystem.DTO.NoticeRegisterResponse;
import com.AFFLE.server.domain.ManageSystem.DTO.NoticeSummaryResponse;
import com.AFFLE.server.domain.ManageSystem.Repository.ElderRepository;
import com.AFFLE.server.domain.ManageSystem.Repository.MeterManRepository;
import com.AFFLE.server.domain.ManageSystem.Repository.NoticeRepository;
import com.AFFLE.server.global.exception.ManageSystemException;
import com.AFFLE.server.global.status.ErrorStatus;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NoticeService {

    private final NoticeRepository noticeRepository;
    private final NotificationRepository notificationRepository;
    private final ElderRepository elderRepository;
    private final MeterManRepository meterManRepository;


    public List<NoticeSummaryResponse> getNoticesForMeterReader(Integer meterManId) {
        List<Notice> notices = noticeRepository.findNoticesForMeterReader(meterManId);

        return notices.stream().map(notice ->
                NoticeSummaryResponse.builder()
                        .id(String.valueOf(notice.getPostId()))
                        .title(notice.getTitle())
                        .author(notice.getMeterMan() != null ? notice.getMeterMan().getName() : "관리자")
                        .date(notice.getDate().toString())
                        .build()
        ).toList();
    }

    public NoticeDetailResponse getNoticeDetail(Integer noticeId) {
        Notice notice = noticeRepository.findById(noticeId)
                .orElseThrow(() -> new ManageSystemException(ErrorStatus.NOTICE_NOT_FOUND));

        return NoticeDetailResponse.builder()
                .id(String.valueOf(notice.getPostId()))
                .title(notice.getTitle())
                .content(notice.getContent())
                .author(notice.getMeterMan() != null ? notice.getMeterMan().getName() : "관리자")
                .date(notice.getDate().toString())
                .attachment(notice.getFileUrl())
                .build();
    }

    public void deleteNoticeByAuthor(Integer noticeId, Integer meterId) {
        Notice notice = noticeRepository.findById(noticeId)
                .orElseThrow(() -> new ManageSystemException(ErrorStatus.NOTICE_NOT_FOUND));

        if (notice.getMeterMan() == null || !notice.getMeterMan().getMetermanId().equals(meterId)) {
            throw new ManageSystemException(ErrorStatus.NOTICE_AUTHOR_MISMATCH);
        }

        noticeRepository.delete(notice);
    }

    @Transactional
    public NoticeRegisterResponse registerNoticeOrNotification(NoticeRegisterRequest request) {

        if (request.getTitle() != null) {
            // 검침원 공지사항 등록

            // target (=검침원 ID) 가 지정 → 존재 여부 확인
            if (request.getTarget() != null) {
                boolean exists = meterManRepository.existsById(request.getTarget());
                if (!exists) {
                    throw new ManageSystemException(ErrorStatus.METER_MAN_NOT_FOUND);
                }
            }

            Notice notice = Notice.builder()
                    .title(request.getTitle())
                    .content(request.getContent())
                    .fileUrl(request.getAttachment())
                    .date(LocalDate.now())
                    .target(request.getTarget()) // 검침원 ID or null
                    .build();

            noticeRepository.save(notice);

            return new NoticeRegisterResponse(notice.getPostId(), notice.getTarget());

        } else {
            // 노인 알림 등록
            List<Elder> targetElders;

            if (request.getTarget() != null) {
                Elder elder = elderRepository.findById(request.getTarget())
                        .orElseThrow(() -> new ManageSystemException(ErrorStatus.ELDER_NOT_FOUND));
                targetElders = List.of(elder);
            } else {
                targetElders = elderRepository.findAll(); // 전체 노인
            }

            for (Elder elder : targetElders) {
                Notification noti = Notification.builder()
                        .elder(elder)
                        .message(request.getContent())
                        .sentAt(LocalDateTime.now())
                        .build();
                notificationRepository.save(noti);
            }

            return new NoticeRegisterResponse(null, request.getTarget());
        }
    }
}

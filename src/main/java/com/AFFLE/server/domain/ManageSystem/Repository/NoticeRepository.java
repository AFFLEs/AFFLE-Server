package com.AFFLE.server.domain.ManageSystem.Repository;

import com.AFFLE.server.domain.Entity.Notice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface NoticeRepository extends JpaRepository<Notice, Integer> {
    @Query("SELECT n FROM Notice n WHERE n.target IS NULL OR n.target = :meterManId")
    List<Notice> findNoticesForMeterReader(@Param("meterManId") Integer meterManId);
}

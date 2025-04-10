package com.AFFLE.server.domain.ManageElderly.Repository;

import com.AFFLE.server.domain.Entity.ElderMeterMan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ElderMeterManRepository extends JpaRepository<ElderMeterMan, Long> {
    List<ElderMeterMan> findByMeterMan_NameContaining(String name);
}
package com.AFFLE.server.domain.ManageSystem.Repository;

import com.AFFLE.server.domain.Entity.MeterMan;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MeterManRepository extends JpaRepository<MeterMan, Integer> {
    boolean existsByContact(String contact);
}
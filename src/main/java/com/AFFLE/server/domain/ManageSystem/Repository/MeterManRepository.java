package com.AFFLE.server.domain.ManageSystem.Repository;

import com.AFFLE.server.domain.Entity.MeterMan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MeterManRepository extends JpaRepository<MeterMan, Integer> {
    boolean existsByContact(String contact);
    List<MeterMan> findByNameContaining(String keyword);
    Optional<MeterMan> findByName(String name);
}
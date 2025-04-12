package com.AFFLE.server.domain.ManageSystem.Repository;

import com.AFFLE.server.domain.Entity.Elder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ElderRepository extends JpaRepository<Elder, Integer> {
    boolean existsByPhone(String phone);
    List<Elder> findByNameContaining(String name);
    Optional<Elder> findByName(String name);
}

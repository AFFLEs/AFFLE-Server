package com.AFFLE.AFFLE_server.repository;

import com.AFFLE.AFFLE_server.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
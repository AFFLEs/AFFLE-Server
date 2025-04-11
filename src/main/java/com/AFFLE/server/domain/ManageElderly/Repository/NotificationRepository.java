package com.AFFLE.server.domain.ManageElderly.Repository;

import com.AFFLE.server.domain.Entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
}
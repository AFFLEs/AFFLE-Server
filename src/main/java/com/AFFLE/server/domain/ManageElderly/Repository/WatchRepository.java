package com.AFFLE.server.domain.ManageElderly.Repository;

import com.AFFLE.server.domain.Entity.Watch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WatchRepository extends JpaRepository<Watch, Integer> {

    @Query("SELECT w FROM Watch w JOIN FETCH w.elder WHERE w.isWorn = :status")
    List<Watch> findByIsWorn(@Param("status") boolean status);

    @Query("SELECT w FROM Watch w JOIN FETCH w.elder WHERE w.elder.name LIKE %:keyword%")
    List<Watch> findByElderNameContaining(@Param("keyword") String keyword);
}
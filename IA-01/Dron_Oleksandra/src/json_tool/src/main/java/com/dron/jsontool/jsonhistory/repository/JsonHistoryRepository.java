package com.dron.jsontool.jsonhistory.repository;

import com.dron.jsontool.jsonhistory.repository.entity.JsonHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface JsonHistoryRepository extends JpaRepository<JsonHistory, UUID> {
}

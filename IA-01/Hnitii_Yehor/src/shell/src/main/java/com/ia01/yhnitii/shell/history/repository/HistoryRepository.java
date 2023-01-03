package com.ia01.yhnitii.shell.history.repository;

import com.ia01.yhnitii.shell.history.repository.entity.History;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;


public interface HistoryRepository extends JpaRepository<History, UUID> {

}
package com.ia01.yhnitii.shell.command.repository;

import com.ia01.yhnitii.shell.command.repository.entity.Command;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;


public interface CommandRepository extends JpaRepository<Command, UUID> {

}
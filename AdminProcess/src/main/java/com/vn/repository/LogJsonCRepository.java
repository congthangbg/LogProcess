package com.vn.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vn.entity.LogJsonCRequest;
import com.vn.entity.LogJsonCEntity;
import com.vn.entity.LoglocalEntity;

public interface LogJsonCRepository extends JpaRepository<LogJsonCEntity, Long> {

}

package com.vn.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vn.entity.LoglocalEntity;

public interface DataLogJsonRepository extends JpaRepository<LoglocalEntity, Long> {
	List<LoglocalEntity> findByIp(String ip);
}

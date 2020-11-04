package com.lyoonu.myblog.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lyoonu.myblog.model.Board;

public interface BoardRepository extends JpaRepository<Board, Long> {
	
}

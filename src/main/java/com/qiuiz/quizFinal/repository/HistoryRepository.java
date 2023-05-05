package com.qiuiz.quizFinal.repository;

import com.qiuiz.quizFinal.model.HistoryGames;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HistoryRepository extends JpaRepository<HistoryGames,Integer> {
}

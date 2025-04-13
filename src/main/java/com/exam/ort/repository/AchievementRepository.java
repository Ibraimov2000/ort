package com.exam.ort.repository;

import com.exam.ort.entity.Achievement;
import com.exam.ort.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AchievementRepository extends JpaRepository<Achievement, Long> {
    List<Achievement> findAllByUser(User user);
}

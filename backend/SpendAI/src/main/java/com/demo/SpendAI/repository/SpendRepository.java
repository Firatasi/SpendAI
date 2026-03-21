package com.demo.SpendAI.repository;

import com.demo.SpendAI.entity.Spend;
import com.demo.SpendAI.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SpendRepository extends JpaRepository<Spend, Long> {

    List<Spend> findByUserOrderByDateDesc(User user);

    List<Spend> findByUserAndCategory(User user, String category);

    List<Spend> findAllByUserId(Long userId);

    List<Spend> findByUser(User user);

}

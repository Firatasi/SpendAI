package com.demo.SpendAI.repository;

import com.demo.SpendAI.entity.Spend;
import com.demo.SpendAI.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SpendRepository extends JpaRepository<Spend, Long> {

    // Belirli bir kullanıcıya ait tüm harcamaları tarihe göre tersten (en yeni üstte) getirir
    List<Spend> findByUserOrderByDateDesc(User user);

    // İleride AI ile analiz yapmak istersen: Belirli kategorideki harcamaları getir
    List<Spend> findByUserAndCategory(User user, String category);
}

package com.demo.SpendAI.service;

import com.demo.SpendAI.entity.Spend;
import com.demo.SpendAI.entity.User;
import com.demo.SpendAI.repository.SpendRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SpendService {

    private final SpendRepository spendRepository;

    public List<Spend> getAllSpendsByUser(User user) {
        return spendRepository.findByUser(user);
    }
}

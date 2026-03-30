package com.demo.SpendAI.service;

import com.demo.SpendAI.entity.User;
import com.demo.SpendAI.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RewardService {

    @Autowired
    private UserRepository userRepository;

    public void addXp(User user, int amount) {
        int currentXp = user.getXp() != null ? user.getXp() : 0;
        int newXp = currentXp + amount;

        // Basit bir seviye atlama mantığı: Her 100 XP'de bir seviye
        int newLevel = (newXp / 100) + 1;

        user.setXp(newXp);
        user.setLevel(newLevel);
        userRepository.save(user);
    }

    public String getRankName(int level) {
        if (level < 3) return "Para Çaylağı 🥚";
        if (level < 7) return "Bütçe Dostu 💰";
        if (level < 12) return "Tasarruf Ustası 🛡️";
        return "Finansal Guru 👑";
    }
}

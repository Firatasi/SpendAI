package com.demo.SpendAI.controller;

import com.demo.SpendAI.entity.Spend;
import com.demo.SpendAI.entity.User;
import com.demo.SpendAI.repository.SpendRepository;
import com.demo.SpendAI.repository.UserRepository;
import com.demo.SpendAI.service.AiService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Map;

@RestController
@RequestMapping("/api/spends")
public class SpendController {

    private final AiService aiService;
    private final SpendRepository spendRepository;
    private final UserRepository userRepository;
    private final ObjectMapper objectMapper; //JSONU objeye çevirmek için java sınıfı

    public SpendController(AiService aiService, SpendRepository spendRepository,
                           UserRepository userRepository, ObjectMapper objectMapper) {
        this.aiService = aiService;
        this.spendRepository = spendRepository;
        this.userRepository = userRepository;
        this.objectMapper = objectMapper;
    }


    public ResponseEntity<?> addSpendWithAi(@RequestBody Map<String, String> request) {

        String userMessage = request.get("message");

        //aiye göner analiz et
        String aiJson = aiService.analyzeSpending(userMessage);

        try {
            // AI'dan gelen JSON'ı Spend nesnesine çevir
            Spend spend = objectMapper.readValue(aiJson, Spend.class);

            // O anki kullanıcıyı bul (SecurityContext'ten)
            String username = SecurityContextHolder.getContext().getAuthentication().getName();
            User user = userRepository.findByUsername(username).orElseThrow();

            //  İlişkiyi kur ve kaydet
            spend.setUser(user);
            spend.setDate(LocalDateTime.now());
            spendRepository.save(spend);

            return ResponseEntity.ok(spend);
        }catch (Exception e) {
            return ResponseEntity.badRequest().body("AI analizi başarısız oldu: " + e.getMessage());
        }
    }


}

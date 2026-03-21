package com.demo.SpendAI.controller;

import com.demo.SpendAI.entity.Spend;
import com.demo.SpendAI.entity.User;
import com.demo.SpendAI.repository.SpendRepository;
import com.demo.SpendAI.repository.UserRepository;
import com.demo.SpendAI.security.CustomUserDetails;
import com.demo.SpendAI.service.AiService;
import com.demo.SpendAI.service.SpendService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

import java.util.Map;

@RestController
@RequestMapping("/api/spends")
public class SpendController {

    private final SpendService spendService;
    private final AiService aiService;
    private final SpendRepository spendRepository;
    private final UserRepository userRepository;
    private final ObjectMapper objectMapper; //JSONU objeye çevirmek için java sınıfı

    public SpendController(AiService aiService, SpendRepository spendRepository,
                           UserRepository userRepository, ObjectMapper objectMapper,
                           SpendService spendService) {
        this.aiService = aiService;
        this.spendRepository = spendRepository;
        this.userRepository = userRepository;
        this.objectMapper = objectMapper;
        this.spendService = spendService;
    }

    @PostMapping("/ai-add")
    public ResponseEntity<?> addSpendWithAi(@RequestBody Map<String, String> request) {

        String userMessage = request.get("message");
        System.out.println("Gelen Mesaj: " + userMessage); // KONSOLA BAK!

        //aiye göner analiz et
        String aiJson = aiService.analyzeSpending(userMessage);
        System.out.println("AI'dan Gelen Yanıt: " + aiJson); // KONSOLA BAK!

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

    @GetMapping("/getAllSpends")
    public ResponseEntity<?> getAllSpends(Authentication authentication) {
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        String username = userDetails.getUsername();

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Kullanıcı bulunamadı"));

        return ResponseEntity.ok(spendService.getAllSpendsByUser(user));
    }
}

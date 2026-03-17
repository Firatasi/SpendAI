package com.demo.SpendAI.service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

@Service
public class AiService {

    private final ChatClient chatClient;

    public AiService(ChatClient.Builder builder) {
        // AI'ya başlangıçta kim olduğunu ve ne yapacağını öğretiyoruz (System Prompt)
        this.chatClient = builder
                .defaultSystem("Sen bir finans asistanısın. Kullanıcının harcama metinlerini analiz edip " +
                        "miktar, kategori ve açıklama olarak ayıklarsın. Yanıtı sadece JSON formatında ver. " +
                        "Örnek format: {\"amount\": 150.0, \"category\": \"GIDA\", \"description\": \"Market alışverişi\"}")
                .build();
    }

    public String analyzeSpending(String message) {
        return chatClient.prompt()
                .user("Şu harcamayı analiz et ve sadece json dön" + message)
                .call()
                .content();
    }

}

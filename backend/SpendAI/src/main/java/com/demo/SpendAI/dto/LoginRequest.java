package com.demo.SpendAI.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest {

    @NotBlank(message = "Kullanıcı adı boş olamaz!")
    private String username;

    @NotBlank(message = "Şifre boş olamaz!")
    private String password;
}

package com.demo.SpendAI.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class RegisterRequest {
    @Size(min = 3, max = 50)
    @NotBlank(message = "Kullanıcı adı boş olamaz!")
    private String username;

    @Size(min = 3, max = 50)
    @NotBlank(message = "Şifre boş olamaz!")
    private String password;

    @Size(min = 3, max = 150)
    @NotBlank(message = "Email adı boş olamaz!")
    private String email;
}

package com.demo.SpendAI.service;

import com.demo.SpendAI.dto.AuthResponse;
import com.demo.SpendAI.dto.LoginRequest;
import com.demo.SpendAI.dto.RegisterRequest;
import com.demo.SpendAI.entity.Role;
import com.demo.SpendAI.entity.User;
import com.demo.SpendAI.mapper.UserMapper;
import com.demo.SpendAI.repository.UserRepository;
import com.demo.SpendAI.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final UserMapper userMapper;

    public AuthResponse register(RegisterRequest request) {
        // 1. Kullanıcıyı Mapper ile Entity'e çevir
        User user = userMapper.toEntity(request);

        // 2. Şifreyi BCrypt ile güvenli hale getir
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        // 3. Varsayılan rol ata (İlk kayıt olan herkes USER olsun)
        user.setRole(Role.ROLE_USER);

        // 4. Veritabanına kaydet
        userRepository.save(user);

        // 5. Token üret ve dön
        var jwtToken = jwtService.generateToken(new com.demo.SpendAI.security.CustomUserDetails(user));
        return AuthResponse.builder()
                .token(jwtToken)
                .username(user.getUsername())
                .role(user.getRole().name())
                .build();
    }

    public AuthResponse login(LoginRequest request) {
        // 1. Spring Security ile kimlik doğrula (Şifre kontrolünü o yapar)
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );

        // 2. Kullanıcıyı bul
        var user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("Kullanıcı bulunamadı!"));

        // 3. Token üret ve paketleyip dön
        var jwtToken = jwtService.generateToken(new com.demo.SpendAI.security.CustomUserDetails(user));
        return AuthResponse.builder()
                .token(jwtToken)
                .username(user.getUsername())
                .role(user.getRole().name())
                .build();
    }
}

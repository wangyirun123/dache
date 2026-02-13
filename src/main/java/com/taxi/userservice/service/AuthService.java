package com.taxi.userservice.service;

import com.taxi.userservice.entity.User;
import com.taxi.userservice.repository.UserRepository;
import com.taxi.userservice.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    public String sendCode(String phone) {
        return "0000"; // Mock 验证码
    }

    public String loginByCode(String phone, String code) {
        if (!"0000".equals(code)) {
            throw new IllegalArgumentException("验证码错误");
        }

        User user = userRepository.findByPhone(phone)
                .orElseGet(() -> userRepository.save(
                        User.builder()
                                .phone(phone)
                                .passwordHash(passwordEncoder.encode("default"))
                                .type(0)
                                .status(0)
                                .createdAt(LocalDateTime.now())
                                .updatedAt(LocalDateTime.now())
                                .build()
                ));

        return jwtUtil.generateToken(user.getId());
    }
}
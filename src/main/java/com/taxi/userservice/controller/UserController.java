package com.taxi.userservice.controller;

import com.taxi.userservice.common.ApiResponse;
import com.taxi.userservice.dto.UserDTO;
import com.taxi.userservice.entity.User;
import com.taxi.userservice.repository.UserRepository;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Operation(summary = "获取当前登录用户信息")
    @GetMapping("/me")
    public ApiResponse<UserDTO> me(Authentication authentication) {
        Long uid = (Long) authentication.getPrincipal();
        User u = userRepository.findById(uid).orElseThrow(() -> new RuntimeException("用户不存在"));

        UserDTO dto = new UserDTO();
        dto.setId(u.getId());
        dto.setPhone(u.getPhone());
        dto.setNickname(u.getNickname());
        dto.setAvatar(u.getAvatar());
        dto.setType(u.getType());
        dto.setStatus(u.getStatus());
        return ApiResponse.ok(dto);
    }
}
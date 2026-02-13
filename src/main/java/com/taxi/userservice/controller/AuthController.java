package com.taxi.userservice.controller;

import com.taxi.userservice.common.ApiResponse;
import com.taxi.userservice.dto.LoginRequest;
import com.taxi.userservice.dto.LoginResponse;
import com.taxi.userservice.dto.SendCodeRequest;
import com.taxi.userservice.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @Operation(summary = "发送验证码（当前为 mock：返回 0000）")
    @PostMapping("/sendCode")
    public ApiResponse<String> sendCode(@RequestBody @Validated SendCodeRequest req) {
        return ApiResponse.ok(authService.sendCode(req.getPhone()));
    }

    @Operation(summary = "手机号验证码登录")
    @PostMapping("/login")
    public ApiResponse<LoginResponse> login(@RequestBody @Validated LoginRequest req) {
        String token = authService.loginByCode(req.getPhone(), req.getCode());
        return ApiResponse.ok(new LoginResponse(token));
    }
}
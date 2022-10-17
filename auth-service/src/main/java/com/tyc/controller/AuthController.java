package com.tyc.controller;

import com.tyc.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.tyc.constants.ApiUrls.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(AUTH)
public class AuthController {
    private final AuthService service;
    @PostMapping(DOLOGIN)
    public ResponseEntity<Void> doLogin() {
        return ResponseEntity.ok().build();
    }
    @PostMapping(REGISTER)
    public ResponseEntity<Void> register() {
        return ResponseEntity.ok().build();
    }
}

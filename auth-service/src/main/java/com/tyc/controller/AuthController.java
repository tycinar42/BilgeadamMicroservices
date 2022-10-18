package com.tyc.controller;

import com.tyc.dto.request.LoginRequestDto;
import com.tyc.dto.request.RegisterRequestDto;
import com.tyc.repository.entity.Auth;
import com.tyc.repository.enums.Activated;
import com.tyc.repository.enums.Roles;
import com.tyc.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static com.tyc.constants.ApiUrls.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(AUTH)
public class AuthController {
    private final AuthService service;
    @PostMapping(DOLOGIN)
    public ResponseEntity<String > doLogin(@RequestBody @Valid LoginRequestDto dto) {
        return ResponseEntity.ok(service.doLogin(dto));
    }
    @PostMapping(REGISTER)
    public ResponseEntity<Void> register(@RequestBody @Valid RegisterRequestDto dto) {
        if(service.save(dto))
            return ResponseEntity.ok().build();
        return ResponseEntity.badRequest().build();
    }
}

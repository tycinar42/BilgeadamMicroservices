package com.tyc.controller;

import com.tyc.dto.request.LoginRequestDto;
import com.tyc.dto.request.RegisterRequestDto;
import com.tyc.rabbitmq.producer.MessageProducer;
import com.tyc.repository.entity.Auth;
import com.tyc.repository.enums.Activated;
import com.tyc.repository.enums.Roles;
import com.tyc.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.List;

import static com.tyc.constants.ApiUrls.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(AUTH)
public class AuthController {
    private final AuthService service;
    private final MessageProducer messageProducer;
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

    @GetMapping(GETALL)
    public ResponseEntity<List<Auth>> getList() {
        return ResponseEntity.ok(service.findAll());
    }

    @PostMapping(SENDMESSAGE)
    public ResponseEntity<Void> sendMessage(String message, Long code) {
        messageProducer.sendMessage(message, code);
        return ResponseEntity.ok().build();
    }
}

package com.tyc.controller;

import com.tyc.dto.request.GetAllRolesRequestDto;
import com.tyc.dto.request.LoginRequestDto;
import com.tyc.dto.request.RegisterRequestDto;
import com.tyc.dto.response.LoginResponseDto;
import com.tyc.dto.response.RegisterResponseDto;
import com.tyc.rabbitmq.producer.MessageProducer;
import com.tyc.repository.entity.Auth;
import com.tyc.repository.entity.AuthRoles;
import com.tyc.repository.entity.Authorities;
import com.tyc.repository.enums.Activated;
import com.tyc.repository.enums.Roles;
import com.tyc.service.AuthRolesService;
import com.tyc.service.AuthService;
import com.tyc.service.AuthoritiesService;
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
    private final AuthRolesService authRolesService;
    private final AuthoritiesService authoritiesService;
    @PostMapping(DOLOGIN)
    public ResponseEntity<LoginResponseDto> doLogin(@RequestBody @Valid LoginRequestDto dto) {
        String token = service.doLogin(dto);
        return ResponseEntity.ok(LoginResponseDto.builder()
                        .token(token)
                        .code(1201)
                        .message("Giris Basarili")
                .build());
    }
    @PostMapping(REGISTER)
    public ResponseEntity<RegisterResponseDto> register(@RequestBody @Valid RegisterRequestDto dto) {
        if(service.save(dto))
            return ResponseEntity.ok(RegisterResponseDto.builder()
                            .code(1200)
                            .message("Kayit Basarili")
                    .build());
        return ResponseEntity.badRequest().body(RegisterResponseDto.builder()
                        .code(1400)
                        .message("Kayit Basarisiz")
                .build());
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

    @GetMapping("/getallroles")
    public ResponseEntity<List<Authorities>> getAllRoles() {
        return ResponseEntity.ok(authoritiesService.findAll());
    }

    @PostMapping("/saveroles")
    public ResponseEntity<Void> saveRoles(String roleName) {
        authoritiesService.save(Authorities.builder()
                        .name(roleName)
                .build());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/saveauthroles")
    public ResponseEntity<Void> saveAuthRoles(Long authId, Long roleId){
        authRolesService.save(AuthRoles.builder()
                .authId(authId)
                .roleId(roleId)
                .build());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/getallrolesbyauthid")
    public ResponseEntity<List<String>> getAllRolesByAuthId(@RequestBody GetAllRolesRequestDto dto) {
        return ResponseEntity.ok(authRolesService.getRolesByAuthId(dto.getAuthId()));
    }
}

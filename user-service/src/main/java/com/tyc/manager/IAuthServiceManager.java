package com.tyc.manager;

import com.tyc.dto.request.GetAllRolesRequestDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "auth-service-manager",
    url = "${myapplication.auth-service.feign-client}/auth", decode404 = true)
public interface IAuthServiceManager {
    @PostMapping("/getallrolesbyauthid")
    public ResponseEntity<List<String>> getAllRolesByAuthId(@RequestBody GetAllRolesRequestDto dto);
}

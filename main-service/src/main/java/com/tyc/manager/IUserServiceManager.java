package com.tyc.manager;

import com.tyc.dto.request.FindByAuthIdRequestDto;
import com.tyc.dto.response.FindByAuthIdResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "user-profile-service", url = "${myapplication.user-service.feign-client}/user", decode404 = true)
public interface IUserServiceManager {
    @PostMapping("/findbyauthid")
    public ResponseEntity<FindByAuthIdResponseDto> findByAuthId(@RequestBody FindByAuthIdRequestDto dto);
}

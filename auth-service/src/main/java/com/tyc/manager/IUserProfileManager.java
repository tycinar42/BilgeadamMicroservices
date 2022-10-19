package com.tyc.manager;

import com.tyc.dto.request.UserProfileSaveRequestDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import static com.tyc.constants.ApiUrls.SAVE;

/**
 * DIKKAT!!!
 * name -> benzersiz bir isim olmalidir. Yoksa hata aliriz.
 */
@FeignClient(name = "user-profile-service", url = "http://localhost:9092/api/v1/user", decode404 = true)
public interface IUserProfileManager {

    @PostMapping(SAVE)
    public ResponseEntity<Boolean> save(@RequestBody UserProfileSaveRequestDto dto);
}

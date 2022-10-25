package com.tyc.controller;

import com.tyc.dto.request.UserProfileSaveRequestDto;
import com.tyc.dto.request.UserProfileUpdateRequestDto;
import com.tyc.repository.entity.UserProfile;
import com.tyc.service.UserProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.tyc.constants.ApiUrls.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(USER)
public class UserProfileController {

    private final UserProfileService service;


    @GetMapping("/getupper")
    public ResponseEntity<String> getUpperCase(Long authId) {

        return ResponseEntity.ok(service.getUpperCase(authId));
    }

    @PostMapping("/savecacheable")
    public ResponseEntity<Void> updateUser(@RequestBody UserProfile userProfile) {
        service.updateCacheReset(userProfile);
        return ResponseEntity.ok().build();
    }

    /**
     * Kullanici kaydi, auth service'te yapiliyor ve burada olan bilgiler user-service'e gonderiliyor
     * Auth-service'ten gelen parametreler:
     *  1- username
     *  2- email
     *  3- authId
     * @return
     */
    @PostMapping(SAVE)
    public ResponseEntity<Boolean> save(@RequestBody UserProfileSaveRequestDto dto) {
        return ResponseEntity.ok(service.save(dto));
    }

    @PostMapping(UPDATE)
    public ResponseEntity<Boolean> update(@RequestBody UserProfileUpdateRequestDto dto) {
        return ResponseEntity.ok(service.update(dto));
    }

    @PostMapping(FIND_BY_ID)
    public ResponseEntity<Boolean> findById() {
        return null;
    }

    @GetMapping(USER_LIST)
    public ResponseEntity<List<UserProfile>> userList() {
        return ResponseEntity.ok(service.findAll());
    }
}

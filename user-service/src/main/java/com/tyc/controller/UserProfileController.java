package com.tyc.controller;

import com.tyc.dto.request.UserProfileSaveRequestDto;
import com.tyc.dto.request.UserProfileUpdateRequestDto;
import com.tyc.service.UserProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.tyc.constants.ApiUrls.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(USER)
public class UserProfileController {

    private final UserProfileService service;

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

    @PostMapping(USER_LIST)
    public ResponseEntity<Boolean> userList() {
        return null;
    }
}
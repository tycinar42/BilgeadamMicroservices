package com.tyc.controller;

import com.tyc.repository.entity.UserProfile;
import com.tyc.service.UserProfileService;
import lombok.RequiredArgsConstructor;
import org.elasticsearch.common.recycler.Recycler;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.tyc.constants.ApiUrls.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(ELASTIC)
public class UserProfileController {

    private final UserProfileService service;

    @PostMapping(SAVE)
    public ResponseEntity<Void> save(@RequestBody UserProfile userProfile) {
        service.save(userProfile);
        return ResponseEntity.ok().build();
    }

    @PostMapping(UPDATE)
    public ResponseEntity<Void> update(@RequestBody UserProfile userProfile) {
        service.save(userProfile);
        return ResponseEntity.ok().build();
    }

    @GetMapping(GETALL)
    public ResponseEntity<Iterable<UserProfile>> getAll() {
        return ResponseEntity.ok(service.findAll());
    }
}

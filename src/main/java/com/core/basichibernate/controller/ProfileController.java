package com.core.basichibernate.controller;

import com.core.basichibernate.entity.Profile;
import com.core.basichibernate.service.ProfileService;
import com.core.basichibernate.validator.ProfileDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/profile")
@Validated
public class ProfileController {

    @Autowired
    private ProfileService profileService;

    @PostMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<Profile> createProfile(@PathVariable Long id, @Valid @RequestBody ProfileDTO profileDTO) {
        profileDTO.setUserId(id);
        Profile profile = profileService.createProfile(id, profileDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(profile);
    }
}

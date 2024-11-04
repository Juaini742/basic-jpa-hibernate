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

    /**
     * @param userId
     * @return
     * getProfile() adalah metode yang digunakan untuk mendapatkan profil pengguna berdasarkan ID pengguna.
     * Metode ini menerima ID pengguna dan mengembalikan objek Profile yang sesuai.
     * Jika profil tidak ditemukan, metode ini akan melemparkan RuntimeException.
     */
    @GetMapping(value = "/{userId}", produces = "application/json")
    public ResponseEntity<Profile> getProfile(@PathVariable Long userId) {
        Profile profile = profileService.getProfileByUserId(userId);
        if(profile == null) {
            throw new RuntimeException("Profile not found");
        }
        return ResponseEntity.ok(profile);
    }

    /**
     * @param profileDTO
     * @return
     * createProfile() adalah metode yang digunakan untuk membuat profil pengguna baru.
     * Metode ini menerima objek ProfileDTO yang berisi informasi profil yang akan dibuat.
     * Metode ini membuat objek Profile baru, mengisi informasi profil, dan menyimpan profil ke dalam database.
     * Jika pengguna dengan ID yang diberikan tidak ditemukan, metode ini akan melemparkan RuntimeException.
     * Contoh JSON untuk membuat profil pengguna baru:
     * {
     *     "userId": 1,
     *     "firstName": "John",
     *     "lastName": "Doe",
     *     "phone": "1234567890",
     *     "address": "123 Main St, Anytown, USA"
     * }
     */
    @PostMapping( produces = "application/json")
    public ResponseEntity<ProfileDTO> createProfile(@Valid @RequestBody ProfileDTO profileDTO) {
        ProfileDTO profile = profileService.createProfile(profileDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(profile);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<Profile> updateProfile(@PathVariable Long userId, @Valid @RequestBody ProfileDTO profileDTO) {
        Profile profile = profileService.updateProfile(userId, profileDTO);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(profile);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<String> deleteProfile(@PathVariable Long userId) {
        profileService.deleteProfile(userId);
        return ResponseEntity.ok("Profile deleted successfully");
    }
}

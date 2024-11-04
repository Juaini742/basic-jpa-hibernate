package com.core.basichibernate.service;

import com.core.basichibernate.entity.Profile;
import com.core.basichibernate.entity.Users;
import com.core.basichibernate.repository.ProfileRepository;
import com.core.basichibernate.repository.UserRepository;
import com.core.basichibernate.validator.ProfileDTO;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Transectional adalah anotasi yang digunakan untuk mengaktifkan transaksi database.
 * Dengan menggunakan @Transactional, semua operasi database yang terkait dengan metode ini akan dijalankan dalam satu transaksi.
 * Jika terjadi kesalahan dalam operasi database, transaksi akan dibatalkan, dan semua perubahan yang dilakukan sebelumnya akan dikembalikan ke keadaan sebelumnya.
 */
@Service
@Transactional
public class ProfileService {
    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EntityManager entityManager;

    // CREATE
    /**
     * @param profileDTO
     * @return
     * createProfile() adalah metode yang digunakan untuk membuat profil pengguna baru.
     * Metode ini menerima ID pengguna dan objek ProfileDTO yang berisi informasi profil yang akan dibuat.
     * Metode ini membuat objek Profile baru, mengisi informasi profil, dan menyimpan profil ke dalam database.
     * Jika pengguna dengan ID yang diberikan tidak ditemukan, metode ini akan melemparkan RuntimeException.
     * Contoh JSON untuk membuat profil pengguna baru:
     * {
     *     "address": "Jl. Contoh No. 123",
     *     "phone": "08123456789",
     *     "userId": 1
     * }
     */
    public ProfileDTO createProfile(ProfileDTO profileDTO) {
        Users existUser = userRepository.findById(profileDTO.getUserId()).orElseThrow(() -> new IllegalArgumentException("User not found"));

        Profile profile = new Profile();
        profile.setFirstName(profileDTO.getFirstName());
        profile.setLastName(profileDTO.getLastName());
        profile.setPhone(profileDTO.getPhone());
        profile.setAddress(profileDTO.getAddress());
        profile.setUserId(profileDTO.getUserId());

        profile.setUser(existUser);
        existUser.setProfile(profile);

        Profile savedProfile = profileRepository.save(profile);

        existUser.setProfile(savedProfile);
        userRepository.save(existUser);

        ProfileDTO createdProfileDTO = new ProfileDTO();
        createdProfileDTO.setUserId(existUser.getId());
        createdProfileDTO.setFirstName(savedProfile.getFirstName());
        createdProfileDTO.setLastName(savedProfile.getLastName());
        createdProfileDTO.setPhone(savedProfile.getPhone());
        createdProfileDTO.setAddress(savedProfile.getAddress());

        return createdProfileDTO;
    }

    /**
     * READ
     * @param userId
     * @return
     * getProfileById() adalah metode yang digunakan untuk mendapatkan profil pengguna berdasarkan ID pengguna.
     * Metode ini menerima ID pengguna dan mencari profil pengguna yang sesuai dalam database.
     * Jika profil ditemukan, metode ini akan mengembalikan objek Profile yang sesuai.
     * Jika profil tidak ditemukan, metode ini akan melemparkan RuntimeException.
     */
    public Profile getProfileByUserId(Long userId) {
        return profileRepository.findByUserId(userId).orElseThrow(() -> new RuntimeException("Profile not found"));
    }

    // UPDATE
    /**
     * @param userId
     * @param profileDTO
     * updateProfile() adalah metode yang digunakan untuk memperbarui profil pengguna.
     * Metode ini menerima ID pengguna dan objek ProfileDTO yang berisi informasi profil yang akan diperbarui.
     * Metode ini mencari profil pengguna yang sesuai dalam database, memperbarui informasi profil, dan menyimpan profil yang diperbarui ke dalam database.
     * Jika profil tidak ditemukan, metode ini akan melemparkan RuntimeException.
     * Contoh JSON untuk memperbarui profil pengguna:
     * {
     *     "address": "Jl. Contoh No. 456",
     *     "phone": "08987654321",
     *     "userId": 1
     * }
     * @return Profile
     */
    public Profile updateProfile(Long userId, ProfileDTO profileDTO) {
        Profile profile = profileRepository.findByUserId(userId).orElseThrow(() -> new RuntimeException("Profile not found"));
        profile.setFirstName(profileDTO.getFirstName());
        profile.setLastName(profileDTO.getLastName());
        profile.setAddress(profileDTO.getAddress());
        profile.setPhone(profileDTO.getPhone());
        return profileRepository.save(profile);
    }

    // DELETE
    /**
     * @param userId
     * deleteProfile adalah metode yang digunakan untuk menghapus profil pengguna.
     * Metode ini menerima ID pengguna dan mencari profil pengguna yang sesuai dalam database.
     * Jika profil ditemukan, metode ini akan menghapus profil dari database.
     * Jika profil tidak ditemukan, metode ini akan melemparkan RuntimeException.
     * @return Profile
     */
    public void deleteProfile(Long userId) {
        Profile profile = profileRepository.findByUserId(userId).orElseThrow(() -> new RuntimeException("Profile not found"));
        Users users = userRepository.findById(profile.getUserId()).orElseThrow(() -> new RuntimeException("User not found"));
        users.setProfile(null);
        profileRepository.deleteById(profile.getId());
    }

}

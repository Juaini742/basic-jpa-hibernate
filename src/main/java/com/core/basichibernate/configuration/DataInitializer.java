package com.core.basichibernate.configuration;

import com.core.basichibernate.entity.Role;
import com.core.basichibernate.repository.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class DataInitializer {

    /**
     * @param roleRepository
     * @return
     *
     * Metode loadData() adalah metode yang digunakan untuk menginisialisasi data awal.
     * Metode ini memeriksa apakah ada data yang tersimpan dalam database.
     * Jika tidak ada data yang tersimpan, maka metode ini akan membuat beberapa peran (roles) dan menyimpan
     * mereka ke dalam database.
     * Contoh JSON untuk membuat peran baru:
     * {
     *     "name": "ADMIN"
     * }
     *
     * {
     *     "name": "USER"
     * }
     *
     * CommandLineRunner adalah antarmuka yang digunakan untuk menjalankan kode saat aplikasi Spring Boot dimulai.
     * Dalam metode ini, kita menginisialisasi data awal dengan membuat beberapa peran (roles) dan menyimpannya ke dalam database.
     * Jika data sudah ada dalam database, maka metode ini tidak akan melakukan apa-apa.
     */
    @Bean
    public CommandLineRunner loadData(RoleRepository roleRepository) {
        return args -> {
            if(roleRepository.count() == 0) {
                Role admin = new Role("ADMIN");
                Role user = new Role("USER");
                Role guest = new Role("GUEST");

                roleRepository.saveAll(List.of(admin, user, guest));
            }
        };
    }
}

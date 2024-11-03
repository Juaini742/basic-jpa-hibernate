package com.core.basichibernate.repository;

import com.core.basichibernate.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    /**
     * @param roleNames
     * @return
     * findByNameIn(List<String> roleNames): Metode ini digunakan untuk mencari peran berdasarkan nama-nama peran yang diberikan.
     * Metode ini mengambil daftar nama peran dan mencari peran yang sesuai dalam database.
     * Metode ini mengembalikan daftar peran yang sesuai.
     * Contoh penggunaan:
     * List<String> roleNames = Arrays.asList("ADMIN", "USER");
     */
    List<Role> findByNameIn(List<String> roleNames);

    /**
     * @param user
     * @return
     * findByName(String user): Metode ini digunakan untuk mencari peran berdasarkan nama peran yang diberikan.
     * Metode ini mengambil nama peran dan mencari peran yang sesuai dalam database.
     * Metode ini mengembalikan peran yang sesuai.
     * Contoh penggunaan:
     * String user = "ADMIN";
     */
    Optional<Object> findByName(String user);
}

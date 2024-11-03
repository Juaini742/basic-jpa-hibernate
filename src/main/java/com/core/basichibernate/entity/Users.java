package com.core.basichibernate.entity;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String password;

    /**
     * @OneToOne: Menandakan bahwa relasi ini adalah tipe satu-ke-satu antara User dan Profile.
     *
     * @JoinColumn(name = "profile_id"): Mengindikasikan bahwa di tabel users,
     * akan ada kolom bernama profile_id yang digunakan untuk menyimpan ID dari Profile.
     * Ini adalah sisi "pemilik" dari relasi, artinya informasi tentang relasi ini disimpan di tabel users.
     */
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "profile_id")
    @JsonManagedReference
    private Profile profile;

    /**
     * @ManyToMany: Menandakan bahwa relasi ini adalah tipe banyak-ke-banyak.
     * @JoinTable: Digunakan untuk mendefinisikan tabel perantara yang menyimpan informasi relasi antara User dan Role.
     *
     *     name: Nama tabel perantara (user_roles) -> akan otomatis dibuatkan sebagai pivot table -
     *     jadi kita tida perlu membuat table secara manual.
     *
     *     joinColumns: Menyatakan kolom yang menyimpan referensi ke entitas User (user_id).
     *     inverseJoinColumns: Menyatakan kolom yang menyimpan referensi ke entitas Role (role_id).
     */
    @ManyToMany
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    @JsonManagedReference
    private Set<Role> roles = new HashSet<>();

    /**
     * @OneToMany(mappedBy = "user"): Menandakan bahwa relasi ini adalah tipe satu-ke-banyak.
     * mappedBy menunjukkan bahwa entitas Post memiliki properti yang mengelola relasi ini, yaitu user.
     * cascade = CascadeType.ALL: Menentukan bahwa semua operasi (seperti persist, merge, remove) pada User akan diteruskan
     * ke Post. Jadi, jika kita menghapus User, semua Post yang terkait juga akan dihapus.
     */
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Post> posts = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL) // name = "user" -> user adalah nama properti di kelas Post yang mengelola relasi ini.
    private  List<Comment> comments = new ArrayList<>();

    public void getName(@NotBlank(message = "name is required") String name) {

    }
}

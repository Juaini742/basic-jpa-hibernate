package com.core.basichibernate.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "profiles")
public class Profile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false)
    private String phone;

    @Column(nullable = false)
    private String address;

    /**
     * @OneToOne(mappedBy = "profile"): Di sini, kita menunjukkan bahwa Profile adalah sisi tidak memiliki relasi.
     * mappedBy mengacu pada nama properti di kelas User yang mengelola relasi ini.
     * Artinya, Profile tidak menyimpan referensi langsung ke User,
     * tetapi mengetahui bahwa dia dihubungkan melalui properti profile di User.
     */
    @OneToOne(mappedBy = "profile")
    @JsonBackReference
    private Users user;

    /**
     * @ManyToOne: Menandakan bahwa relasi ini adalah tipe banyak-ke-satu. Setiap Post akan memiliki referensi ke satu User.
     * @JoinColumn(name = "user_id"): Mengindikasikan bahwa di tabel posts,
     * akan ada kolom bernama user_id yang digunakan untuk menyimpan ID dari User.
     * Ini adalah sisi pemilik dari relasi, di mana informasi relasi disimpan.
     */
    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users users;
}

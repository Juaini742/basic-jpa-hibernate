package com.core.basichibernate.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.awt.image.ImageProducer;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "posts")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    /**
     * @ManyToOne: Menandakan bahwa relasi ini adalah tipe banyak-ke-satu. Setiap Post akan memiliki referensi ke satu User.
     * @JoinColumn(name = "user_id"): Mengindikasikan bahwa di tabel posts,
     * akan ada kolom bernama user_id yang digunakan untuk menyimpan ID dari User.
     * Ini adalah sisi pemilik dari relasi, di mana informasi relasi disimpan.
     */
    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users user;

    /**
     * @OneToMany(mappedBy = "post", cascade = CascadeType.ALL): Menandakan bahwa relasi ini adalah tipe satu-ke-banyak.
     * Setiap Post dapat memiliki banyak Comment.
     * cascade = CascadeType.ALL: Ini memberitahu Hibernate bahwa setiap operasi yang dilakukan pada Post akan direfleksikan pada Comment-nya.
     * Ini berarti, jika Anda menghapus Post, Hibernate juga akan menghapus semua Comment yang terkait dengan Post tersebut.
     */
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private List<Comment> comments = new ArrayList<>();
}

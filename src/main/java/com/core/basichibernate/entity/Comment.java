package com.core.basichibernate.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "comments")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String content;

    /**
     * @ManyToOne: Menandakan bahwa relasi ini adalah tipe banyak-ke-satu. Setiap Post akan memiliki referensi ke satu User.
     * @JoinColumn(name = "user_id"): Mengindikasikan bahwa di tabel posts,
     * akan ada kolom bernama user_id yang digunakan untuk menyimpan ID dari U  ser.
     * Ini adalah sisi pemilik dari relasi, di mana informasi relasi disimpan.
     */
    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users user;

    /**
     * @ManyToOne: Menandakan bahwa relasi ini adalah tipe banyak-ke-satu. Setiap Post akan memiliki referensi ke satu User.
     * @JoinColumn(name = "post_id"): Mengindikasikan bahwa di tabel posts,
     * akan ada kolom bernama post_id yang digunakan untuk menyimpan ID dari Post.
     * Ini adalah sisi pemilik dari relasi, di mana informasi relasi disimpan.
     */
    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;



}

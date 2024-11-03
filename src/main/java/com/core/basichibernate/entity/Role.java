package com.core.basichibernate.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@RequiredArgsConstructor
@NoArgsConstructor(force = true)
@Entity
@Table(name = "roles")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private final String name;

    /**
     * @ManyToMany(mappedBy = "roles"): Menandakan bahwa relasi ini juga adalah tipe banyak-ke-banyak,
     * tetapi sisi ini tidak memiliki tabel join (karena dikelola oleh entitas User).
     */
    @ManyToMany(mappedBy = "roles")
    @JsonIgnore
    private Set<Users> users = new HashSet<>();
}

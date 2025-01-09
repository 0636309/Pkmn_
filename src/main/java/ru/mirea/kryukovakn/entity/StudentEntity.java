package ru.mirea.kryukovakn.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "students")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StudentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(name = "family_name", nullable = false)
    private String familyName;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "sur_name", nullable = false)
    private String surName;

    @Column(name = "\"group\"", nullable = false)
    private String group;

    @OneToMany(mappedBy = "pokemonOwner", cascade = CascadeType.ALL, orphanRemoval = false, fetch = FetchType.LAZY)
    private List<CardEntity> cards;


}

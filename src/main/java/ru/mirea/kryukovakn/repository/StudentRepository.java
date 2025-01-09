package ru.mirea.kryukovakn.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.mirea.kryukovakn.entity.StudentEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface StudentRepository extends JpaRepository<StudentEntity, UUID> {

    List<StudentEntity> findByFirstNameAndSurNameAndFamilyNameAndGroup(String firstName, String surName, String familyName, String group);

    List<StudentEntity> findByGroup(String group);

    void deleteCardById(UUID id);

    boolean existsById(UUID id);

    List<StudentEntity> findByFirstNameAndSurNameAndFamilyName(String firstName, String surName, String familyName);



}



package ru.mirea.kryukovakn.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.mirea.kryukovakn.entity.StudentEntity;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Student implements Serializable {

    private String firstName;
    private String surName;
    private String familyName;
    private String group;
    public static final long serialVersionUID = 1L;

    @Override
    public String toString() {
        return familyName + " " + firstName + " " + surName + ", " + group;
    }

    public static Student fromEntity(StudentEntity studentEntity) {
        return Student.builder()
                .familyName(studentEntity.getFamilyName())
                .firstName(studentEntity.getFirstName())
                .surName(studentEntity.getSurName())
                .group(studentEntity.getGroup())
                .build();

    }

    public static StudentEntity toEntity(Student student) {
        return StudentEntity.builder()
                .familyName(student.getFamilyName())
                .firstName(student.getFirstName())
                .surName(student.getSurName())
                .group(student.getGroup())
                .build();
    }


}

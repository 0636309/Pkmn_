package ru.mirea.kryukovakn.services;

import ru.mirea.kryukovakn.models.Student;

import java.util.List;
import java.util.UUID;

public interface StudentService {

    List<Student> getStudentsByGroup(String group);

    Student saveStudent(Student student);

    List<Student> getAllStudents();


    Student getStudentByFullName(String firstName, String surName, String familyName);


}

package ru.mirea.kryukovakn.dao;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import ru.mirea.kryukovakn.entity.StudentEntity;
import ru.mirea.kryukovakn.repository.StudentRepository;

import javax.management.InstanceNotFoundException;
import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class StudentDao {

    private final StudentRepository studentRepository;

    @SneakyThrows
    public StudentEntity getStudentByFullName(String firstName, String surName, String familyName) {
        List<StudentEntity> students = studentRepository.findByFirstNameAndSurNameAndFamilyName(firstName, surName, familyName);
        if (students.isEmpty()) {
            throw new RuntimeException("Студент не нейден. ");
        } else if (students.size() > 1) {
            throw new RuntimeException("У студента разводение личности.");
        }
        return students.get(0);
    }

    @SneakyThrows
    public List<StudentEntity> getStudentsByGroup(String group) {
        List<StudentEntity> students = studentRepository.findByGroup(group);
        if (students.isEmpty()) {
            throw new InstanceNotFoundException("Студенты из группы " + group + " не найдены.");
        }
        return students;
    }

    public StudentEntity saveStudent(StudentEntity studentEntity) {
        return studentRepository.save(studentEntity);
    }

    public List<StudentEntity> getAllStudents() {
        return studentRepository.findAll();
    }

    public void deleteStudentById(UUID id) {
        if (!studentRepository.existsById(id)) {
            throw new RuntimeException("Студент с ID: " + id + " не найден.");
        }
        studentRepository.deleteCardById(id);
    }

    public StudentEntity getStudentByFullNameAndGroup(String firstName, String surName, String familyName, String group) {
        List<StudentEntity> students = studentRepository.findByFirstNameAndSurNameAndFamilyNameAndGroup(firstName, surName, familyName, group);
        if (students.isEmpty()) {
            return null;
        } else if (students.size() > 1) {
            throw new RuntimeException("?");
        }
        return students.get(0);
    }

    public StudentEntity getStudentById(UUID id) {
        return studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Студент с  " + id + " не найден."));
    }


}

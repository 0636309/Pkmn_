package ru.mirea.kryukovakn.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.mirea.kryukovakn.dao.StudentDao;
import ru.mirea.kryukovakn.entity.StudentEntity;
import ru.mirea.kryukovakn.models.Student;

import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {

    private final StudentDao studentDao;

    @Override
    public Student getStudentByFullName(String firstName, String surName, String familyName){
        StudentEntity entity = studentDao.getStudentByFullName(firstName, surName, familyName);
        return Student.fromEntity(entity);
    }

    @Override
    public List<Student> getStudentsByGroup(String group){
        List<StudentEntity> studentsEntities = studentDao.getStudentsByGroup(group);
        return studentsEntities.stream().map(Student::fromEntity).collect(Collectors.toList());
    }

    @Override
    public Student saveStudent(Student student) {
        List<StudentEntity> sameFio = studentDao.getStudentByFullNameAndGroup(student.getFirstName(), student.getSurName(), student.getFamilyName(), student.getGroup()) == null ? List.of() : List.of(studentDao.getStudentByFullNameAndGroup(student.getFirstName(), student.getSurName(), student.getFamilyName(), student.getGroup()));

        if (!sameFio.isEmpty()) {throw new RuntimeException("Студент существует.");}

        StudentEntity saved = studentDao.saveStudent(Student.toEntity(student));
        return Student.fromEntity(saved);
    }

    @Override
    public List<Student> getAllStudents() {
        return studentDao.getAllStudents().stream()
                .map(Student::fromEntity)
                .toList();
    }


}

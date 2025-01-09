package ru.mirea.kryukovakn.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.mirea.kryukovakn.models.Student;
import ru.mirea.kryukovakn.services.StudentService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/students")
@RequiredArgsConstructor
public class StudentController {

    private final StudentService studentService;

    // норм
    @GetMapping("/all")
    public List<Student> getAllStudents() {
        return studentService.getAllStudents();
    }

    // возможно косяк в колонке с группой
    @GetMapping("/{group}")
    public List<Student> getStudentsByGroup(@PathVariable String group) {
        return studentService.getStudentsByGroup(group);
    }

    // норм
    @GetMapping("")
    public Student getStudentByFullName(@RequestBody Student request) {
        return studentService.getStudentByFullName(
                request.getFirstName(),
                request.getSurName(),
                request.getFamilyName()
        );
    }


    // тоже с группой что-то
    @PostMapping("")
    public Student createStudent(@RequestBody Student request) {
        return studentService.saveStudent(request);
    }
}

package com.example.BehaviorBreeze.controllers;

import com.example.BehaviorBreeze.daos.StudentDao;
import com.example.BehaviorBreeze.models.Student;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
@CrossOrigin
@RestController
@RequestMapping("/students")
public class StudentController {
    private final StudentDao studentDao;
    public StudentController(StudentDao studentDao) {
        this.studentDao = studentDao;
    }

    @GetMapping
    public List<Student> listAllStudents() {
        return studentDao.listAllStudents();
    }

    @GetMapping("/{id}")
    public Student getStudentById(@PathVariable int id) {
        Student student = studentDao.getStudentById(id);
        if (student == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Student not found");
        } else {
            return student;
        }
    }

    @PostMapping
    public Student createStudent(@RequestBody Student student){
        return studentDao.createStudent(student);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus (HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void deleteStudent(@PathVariable int id) {
        int rowsAffected = studentDao.deleteStudentById(id);
        if (rowsAffected == 0) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Student not found", null);
        }
    }
}

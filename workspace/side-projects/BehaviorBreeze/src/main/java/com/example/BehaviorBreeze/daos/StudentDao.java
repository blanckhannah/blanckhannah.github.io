package com.example.BehaviorBreeze.daos;

import com.example.BehaviorBreeze.models.Student;

import java.util.List;

public interface StudentDao {
    List<Student> listAllStudents();
    Student createStudent(Student student);
    int deleteStudentById(int id);

    Student getStudentById(int id);
}

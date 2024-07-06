package com.example.BehaviorBreeze.controllers;

import com.example.BehaviorBreeze.daos.TeacherDao;
import com.example.BehaviorBreeze.models.Teacher;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
@CrossOrigin
@RestController
@RequestMapping("/teachers")
public class TeacherController {
    private TeacherDao teacherDao;

    public TeacherController(TeacherDao teacherDao) {
        this.teacherDao = teacherDao;
    }

    @GetMapping
    public List<Teacher> listTeachers() {
        return teacherDao.listTeachers();
    }

    @GetMapping("/{id}")
    public Teacher getTeacherById(@PathVariable int id) {
        Teacher teacher = teacherDao.getTeacherById(id);
        if(teacher == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Teacher not found");
        } else {
            return teacher;
        }
    }

    @PostMapping
    public Teacher createTeacher(@RequestBody Teacher teacher) {
        return teacherDao.createTeacher(teacher);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void deleteTeacher(@PathVariable int id) {
        int rowsAffected = teacherDao.deleteTeacherById(id);
        if(rowsAffected == 0) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Teacher not found", null);
        }
    }
}

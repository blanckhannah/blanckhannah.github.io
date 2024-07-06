package com.example.BehaviorBreeze.daos;

import com.example.BehaviorBreeze.models.Teacher;

import java.util.List;

public interface TeacherDao {
    List<Teacher> listTeachers();
    Teacher createTeacher(Teacher teacher);
    int deleteTeacherById(int id);

    Teacher getTeacherById(int id);
}

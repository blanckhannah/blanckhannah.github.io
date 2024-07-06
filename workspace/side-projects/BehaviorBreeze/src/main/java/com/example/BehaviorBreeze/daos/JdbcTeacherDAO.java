package com.example.BehaviorBreeze.daos;

import com.example.BehaviorBreeze.exceptions.DaoException;
import com.example.BehaviorBreeze.models.Teacher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcTeacherDAO implements TeacherDao {
    private JdbcTemplate jdbcTemplate;
    public JdbcTeacherDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    private Logger log = LoggerFactory.getLogger(getClass());
    @Override
    public List<Teacher> listTeachers() {
        List<Teacher> teachers = new ArrayList<>();

        String sql = "SELECT * FROM Teacher";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql);

        while(results.next()) {
            Teacher teacher = mapRowToTeacher(results);
            teachers.add(teacher);
        }
        return teachers;
    }

    public Teacher getTeacherById(int id) {
        Teacher teacher = null;
        String sql = "SELECT * FROM teacher WHERE teacher_id = ?";
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, id);
            if(results.next()) {
                teacher = mapRowToTeacher(results);
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
        return teacher;
    }

    @Override
    public Teacher createTeacher(Teacher teacher) {
        Teacher newTeacher = null;

        String sql = "INSERT INTO teacher (first_name, last_name, grade_level) " +
                "VALUES (?, ?, ?) RETURNING teacher_id;";
        try {
            int newTeacherId = jdbcTemplate.queryForObject(sql, int.class, teacher.getFirstName(),
                    teacher.getLastName(), teacher.getGradeLevel());
            newTeacher = getTeacherById(newTeacherId);
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
        return newTeacher;
    }

    @Override
    public int deleteTeacherById(int id) {
        int numberOfRows = 0;
        String deleteStudentTeacherSql = "DELETE FROM student_teacher WHERE teacher_id = ?;";
        String deleteTeacherSql = "DELETE FROM teacher WHERE teacher_id = ?;";
        try {
            jdbcTemplate.update(deleteStudentTeacherSql, id);
            numberOfRows = jdbcTemplate.update(deleteTeacherSql, id);
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
        return numberOfRows;
    }

    public Teacher mapRowToTeacher (SqlRowSet result) {
        Teacher teacher = new Teacher();
        teacher.setId(result.getInt("teacher_id"));
        teacher.setFirstName(result.getString("first_name"));
        teacher.setLastName(result.getString("last_name"));
        teacher.setLastName(result.getString("grade_level"));
        return teacher;
    }
}

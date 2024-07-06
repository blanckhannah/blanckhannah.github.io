package com.example.BehaviorBreeze.daos;

import com.example.BehaviorBreeze.exceptions.DaoException;
import com.example.BehaviorBreeze.models.Student;
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
public class JdbcStudentDAO implements StudentDao{
    private JdbcTemplate jdbcTemplate;
    public JdbcStudentDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    private Logger log = LoggerFactory.getLogger(getClass());
    @Override
    public List<Student> listAllStudents() {
        List<Student> students = new ArrayList<>();
        String sql = "SELECT * FROM student";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
        while (results.next()) {
            Student student = mapRowToStudent(results);
            students.add(student);
        }
        return students;
    }

    public Student getStudentById(int id) {
        Student student = null;
        String sql = "SELECT * FROM student WHERE student_id = ?";
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, id);
            if (results.next()) {
                student = mapRowToStudent(results);
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
        return student;
    }

    @Override
    public Student createStudent(Student student) {
        Student newStudent = null;

        String sql = "INSERT INTO student (first_name, last_name) " +
                "VALUES (?, ?) RETURNING student_id;";
        try {
            int newStudentId = jdbcTemplate.queryForObject(sql, int.class, student.getFirstName(), student.getLastName());
            newStudent = getStudentById(newStudentId);
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
        return newStudent;
    }

    @Override
    public int deleteStudentById(int id) {
        int numberOfRows = 0;
        String deleteStudentTeacherSql = "DELETE FROM student_teacher WHERE student_id = ?;";
        String deleteStudentSql = "DELETE FROM student WHERE student_id = ?;";

        try {
            jdbcTemplate.update(deleteStudentTeacherSql, id);
            numberOfRows = jdbcTemplate.update(deleteStudentSql, id);
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
        return numberOfRows;
    }

    public Student mapRowToStudent (SqlRowSet result) {
        Student student = new Student();
        student.setId(result.getInt("student_id"));
        student.setFirstName(result.getString("first_name"));
        student.setLastName(result.getString("last_name"));
        return student;
    }
}

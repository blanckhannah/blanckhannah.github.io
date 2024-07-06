package com.example.BehaviorBreeze.daos;

import com.example.BehaviorBreeze.exceptions.DaoException;
import com.example.BehaviorBreeze.models.Behavior;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcBehaviorDAO implements BehaviorDao {
    private JdbcTemplate jdbcTemplate;
    public JdbcBehaviorDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    private Logger log = LoggerFactory.getLogger(getClass());
    @Override
    public List<Behavior> listBehaviors() {
        List<Behavior> behaviors = new ArrayList<>();

        String sql = "SELECT * FROM behavior";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql);

        while(results.next()) {
            Behavior behavior = mapRowToBehavior(results);
            behaviors.add(behavior);
        }
        return behaviors;
    }

    public List<String> getUniqueBehaviors(int id) {
        List<String> behaviors = new ArrayList<>();
        String sql = "SELECT DISTINCT action FROM behavior WHERE student_id = ?";
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, id);
            while (results.next()) {
                String behaviorResult = results.getString("action");
                behaviors.add(behaviorResult);
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
        return behaviors;
    }
    public List<Behavior> listBehaviorByStudentId(int id) {
        List<Behavior> behaviors = new ArrayList<>();
        String sql = "SELECT * FROM behavior WHERE student_id = ?";
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, id);
            while (results.next()) {
                Behavior behaviorResult = mapRowToBehavior(results);
                behaviors.add(behaviorResult);
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
        return behaviors;
    }

    public Behavior getBehaviorById(int id) {
        Behavior behavior = null;
        String sql = "SELECT * FROM behavior WHERE behavior_id = ?";
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, id);
            if(results.next()) {
                behavior = mapRowToBehavior(results);
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
        return behavior;
    }

    @Override
    public Behavior createBehavior(Behavior behavior) {
        Behavior newBehavior = null;

        String sql = "INSERT INTO behavior (student_id, action, time) " +
                "VALUES (?, ?, ?) RETURNING behavior_id;";
        try {
            int newBehaviorId = jdbcTemplate.queryForObject(sql, int.class, behavior.getStudentId(), behavior.getAction(),
                    behavior.getTime());
            newBehavior = getBehaviorById(newBehaviorId);
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
        return newBehavior;
    }

    @Override
    public int countBehaviors(String action) {
        return 0;
    }

    @Override
    public int deleteBehavior(int id) {
        int numberOfRows = 0;
        String deleteBehaviorSql = "DELETE FROM behavior WHERE behavior_id = ?;";
        try {
            numberOfRows = jdbcTemplate.update(deleteBehaviorSql, id);
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
        return numberOfRows;
    }

    @Override
    public int countBehaviors(int studentId, String action) {
        String sql = "SELECT COUNT (*) FROM behavior WHERE student_id = ? AND action = ?;";
        try {
            return jdbcTemplate.queryForObject(sql, new Object[]{studentId, action}, Integer.class);
    } catch (DataAccessException e) {
            Logger logger = LoggerFactory.getLogger(getClass());
            logger.error("Error counting behaviors for student ID {} and action {}: {}", studentId, action, e.getMessage());
            return 0;
        }

    }

    @Override
    public int plusOne() {
        return 0;
    }

    @Override
    public int minusOne() {
        return 0;
    }

    @Override
    public int resetCount() {
        return 0;
    }

    public Behavior mapRowToBehavior (SqlRowSet result) {
        Behavior behavior = new Behavior();
        behavior.setId(result.getInt("behavior_id"));
        behavior.setStudentId(result.getInt("student_id"));
        behavior.setAction(result.getString("action"));
        behavior.setTime(result.getTimestamp("time"));
        return behavior;
    }
}

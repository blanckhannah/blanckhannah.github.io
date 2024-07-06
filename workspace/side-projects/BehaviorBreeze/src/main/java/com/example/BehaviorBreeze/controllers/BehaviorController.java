package com.example.BehaviorBreeze.controllers;

import com.example.BehaviorBreeze.daos.BehaviorDao;
import com.example.BehaviorBreeze.models.Behavior;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.service.annotation.GetExchange;

import java.util.ArrayList;
import java.util.List;
@CrossOrigin
@RestController
@RequestMapping("/behaviors")
public class BehaviorController {
    private final BehaviorDao behaviorDao;

    public BehaviorController(BehaviorDao behaviorDao) {
        this.behaviorDao = behaviorDao;
    }

    @GetMapping
    public List<Behavior> listBehaviors() {
        return behaviorDao.listBehaviors();
    }

    @GetMapping("/{id}")
    public Behavior getBehaviorById(@PathVariable int id) {
        Behavior behavior = behaviorDao.getBehaviorById(id);
        if (behavior == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Student not found");
        } else {
            return behavior;
        }
    }

    @GetMapping("/byStudentId")
    public List<Behavior> getBehaviorByStudentId(@RequestParam int id) {
        List<Behavior> behaviors = behaviorDao.listBehaviorByStudentId(id);
        if (behaviors == null || behaviors.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Discipline for student not found");
        } else {
            return behaviors;
        }
    }

    @GetMapping("/unique")
    public List<String> getUniqueBehaviors(@RequestParam int id) {
        List<String> behaviors = behaviorDao.getUniqueBehaviors(id);
        if (behaviors == null || behaviors.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Discipline for student not found");
        } else {
            return behaviors;
        }
    }
    @GetMapping("/count")
    public int countBehaviorsByStudentId(@RequestParam int id, @RequestParam String action) {
        try {
            return behaviorDao.countBehaviors(id, action);
        } catch (Exception e) {
            return 0;
        }
    }

    @PostMapping
    public Behavior createBehavior(@RequestBody Behavior behavior) {
        return behaviorDao.createBehavior(behavior);
    }

    @ResponseStatus (HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void deleteBehavior(@PathVariable int id) {
        int rowsAffected = behaviorDao.deleteBehavior(id);
        if(rowsAffected == 0) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Behavior not found", null);
        }
    }
}


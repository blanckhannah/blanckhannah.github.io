package com.example.BehaviorBreeze.daos;

import com.example.BehaviorBreeze.models.Behavior;

import java.util.List;

public interface BehaviorDao {
    List<Behavior> listBehaviors();
    Behavior createBehavior(Behavior behavior);
    int countBehaviors(String action);
    int deleteBehavior(int id);

    int countBehaviors(int studentId, String action);

    int plusOne();
    int minusOne();
    int resetCount();

    Behavior getBehaviorById(int id);

    List<Behavior> listBehaviorByStudentId(int id);

    List<String> getUniqueBehaviors(int id);
}

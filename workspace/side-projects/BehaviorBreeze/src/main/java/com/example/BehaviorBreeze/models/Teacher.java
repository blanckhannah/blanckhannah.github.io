package com.example.BehaviorBreeze.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Teacher {
    private int id;
    private String firstName;
    private String lastName;
    private String gradeLevel;
}

package com.example.BehaviorBreeze.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Behavior {
    private int id;
    private int studentId;
    private String action;
    private Date time;

}

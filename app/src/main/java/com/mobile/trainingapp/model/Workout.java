package com.mobile.trainingapp.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Workout implements Serializable {

    private String tid;
    private HashMap<String, Exercice> exercices;

    public Workout(String tid, HashMap<String, Exercice> exercices) {
        this.tid = tid;
        this.exercices = exercices;
    }

    public Workout() {
    }

    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid;
    }

    public HashMap<String, Exercice> getExercices() {
        return exercices;
    }

    public void setExercices(HashMap<String, Exercice> exercices) {
        this.exercices = exercices;
    }

    @Override
    public String toString() {
        return "Workout{" +
                "tid='" + tid + '\'' +
                ", exercices=" + exercices +
                '}';
    }
}

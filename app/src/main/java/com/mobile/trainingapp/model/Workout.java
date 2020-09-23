package com.mobile.trainingapp.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Workout implements Serializable {

    private String tid;
    private String tname;
    private List<Exercice> exercices;

    public Workout(String tname) {
        this.tname = tname;
        this.exercices = new ArrayList<>();
    }

    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid;
    }

    public String getTname() {
        return tname;
    }

    public void setTname(String tname) {
        this.tname = tname;
    }

    public List<Exercice> getExercices() {
        return exercices;
    }

    public void setExercices(List<Exercice> exercices) {
        this.exercices = exercices;
    }
}

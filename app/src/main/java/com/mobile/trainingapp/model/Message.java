package com.mobile.trainingapp.model;

import java.io.Serializable;

public class Message implements Serializable {

    private User user;
    private Workout workout;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Workout getWorkout() {
        return workout;
    }

    public void setWorkout(Workout workout) {
        this.workout = workout;
    }
}

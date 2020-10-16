package com.mobile.trainingapp;

import com.mobile.trainingapp.model.Exercice;
import com.mobile.trainingapp.model.Message;
import com.mobile.trainingapp.model.User;
import com.mobile.trainingapp.model.Workout;

import java.util.ArrayList;
import java.util.List;

public class Mock {

    private static Mock mock =  null;
    private static List<Message> messages = new ArrayList<>();

    private Mock(){

    }

    public static Mock getInstance(){
        if (mock == null) mock = new Mock();
        return mock;
    }

    public List<User> mockUsers(){
        List<User> itens = new ArrayList(){};

        itens.add(new User("Elenilson"));
        itens.add(new User("Samuel"));
        itens.add(new User("José"));
        itens.add(new User("Maria"));
        itens.add(new User("Francisco"));
        itens.add(new User("Pedro"));
        itens.add(new User("Vinicius"));
        itens.add(new User("Jose"));
        itens.add(new User("Mario"));

        return itens;
    }

    public List<Exercice> mockExecice(){
        List<Exercice> itens = new ArrayList<>();

        itens.add(new Exercice("Levantento",5));
        itens.add(new Exercice("Levantento",5));
        itens.add(new Exercice("Levantento",5));
        itens.add(new Exercice("Levantento",5));
        itens.add(new Exercice("Levantento",5));
        itens.add(new Exercice("Levantento",5));
        itens.add(new Exercice("Levantento",5));

        return itens;
    }

    public List<Workout> mockWorkouts(){
        List<Workout> itens = new ArrayList<>();

//        itens.add(new Workout("Pernas"));
//        itens.add(new Workout("Glúteos"));
//        itens.add(new Workout("Braço"));
//        itens.add(new Workout("Abdomen"));
//        itens.add(new Workout("Agachamento"));
//        itens.add(new Workout("Prancha"));

        return itens;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        Mock.messages = messages;
    }
}

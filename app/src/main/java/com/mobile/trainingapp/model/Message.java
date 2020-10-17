package com.mobile.trainingapp.model;

import java.io.Serializable;

public class Message implements Serializable {

    private String uidEnviou;
    private String nomeEnviou;
    private String uidRecebeu;
    private String workout;

    public Message() {
    }

    public Message(String uidEnviou, String uidRecebeu, String workout) {
        this.uidEnviou = uidEnviou;
        this.uidRecebeu = uidRecebeu;
        this.workout = workout;
    }

    public Message(String uidEnviou, String nomeEnviou, String uidRecebeu, String workout) {
        this.uidEnviou = uidEnviou;
        this.nomeEnviou = nomeEnviou;
        this.uidRecebeu = uidRecebeu;
        this.workout = workout;
    }

    public String getNomeEnviou() {
        return nomeEnviou;
    }

    public void setNomeEnviou(String nomeEnviou) {
        this.nomeEnviou = nomeEnviou;
    }

    public String getUidEnviou() {
        return uidEnviou;
    }

    public void setUidEnviou(String uidEnviou) {
        this.uidEnviou = uidEnviou;
    }

    public String getUidRecebeu() {
        return uidRecebeu;
    }

    public void setUidRecebeu(String uidRecebeu) {
        this.uidRecebeu = uidRecebeu;
    }

    public String getWorkout() {
        return workout;
    }

    public void setWorkout(String workout) {
        this.workout = workout;
    }
}

package com.mobile.trainingapp.model;

import java.io.Serializable;

public class Exercice implements Serializable {

    private String eid;
    private String ename;
    private Integer erepetition;

    public Exercice(String ename, Integer erepetition) {
        this.ename = ename;
        this.erepetition = erepetition;
    }

    public Exercice() {
    }

    public String getEid() {
        return eid;
    }

    public void setEid(String eid) {
        this.eid = eid;
    }

    public String getEname() {
        return ename;
    }

    public void setEname(String ename) {
        this.ename = ename;
    }

    public Integer getErepetition() {
        return erepetition;
    }

    public void setErepetition(Integer erepetition) {
        this.erepetition = erepetition;
    }

    @Override
    public String toString() {
        return "Exercice{" +
                "ename='" + ename + '\'' +
                ", erepetition=" + erepetition +
                '}';
    }
}

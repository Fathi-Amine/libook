package com.libook.models;

import java.util.List;

public class Auteur {
    private String name;
    private int serialNumber;

    public Auteur(String name, int serialNumber) {
        this.name = name;
        this.serialNumber = serialNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(int serialNumber) {
        this.serialNumber = serialNumber;
    }

    public void createAuteur(Auteur auteur) {

    }

    public Auteur getAuteurByName(String name) {
    }

    public void updateAuteur(String name, String newName) {

    }

    public void deleteAuteur(String name) {

    }

    public List<Auteur> getAuteurs() {

    }

}


package it.aresta.taxcodeApplication.model;

import java.util.Date;

public class Person {

    private String name;
    private String surname;
    private Date birthDate;
    private String gender;
    private String birthCity;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

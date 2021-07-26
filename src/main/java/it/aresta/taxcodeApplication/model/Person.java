package it.aresta.taxcodeApplication.model;

import com.fasterxml.jackson.annotation.JsonInclude;

import javax.validation.constraints.NotBlank;
import java.util.Date;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Person {

    @NotBlank
    private String name;

    @NotBlank
    private String surname;

    @NotBlank
    private Date birthDate;

    @NotBlank
    private String gender;

    @NotBlank
    private String birthCity;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }
}

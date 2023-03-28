package com.alazarska.peopleapplication.apitests.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.With;

@Data
@AllArgsConstructor
@NoArgsConstructor
@With
public class Person {
    private Integer id;
    private String firstName;
    private String lastName;
    private String email;
    @JsonProperty("dob")
    private String dateOfBirth;
    private Float salary;

    public Person(String firstName, String lastName, String email, String dateOfBirth, Float salary) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.dateOfBirth = dateOfBirth;
        this.salary = salary;
    }
}

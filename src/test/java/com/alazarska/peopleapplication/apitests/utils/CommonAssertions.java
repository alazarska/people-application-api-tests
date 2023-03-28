package com.alazarska.peopleapplication.apitests.utils;

import com.alazarska.peopleapplication.apitests.model.Person;
import org.assertj.core.api.SoftAssertions;

public class CommonAssertions {

    public static void assertExpectedPersonDataInHttpResponse(MyHttpResponse<Person> response, Person person) {
        assertSamePeople(response.getBody(), person);
    }

    public static void assertSamePeople(Person response, Person person) {
        SoftAssertions softAssertions = new SoftAssertions();
        softAssertions.assertThat(response.getFirstName()).isEqualTo(person.getFirstName());
        softAssertions.assertThat(response.getLastName()).isEqualTo(person.getLastName());
        softAssertions.assertThat(response.getEmail()).isEqualTo(person.getEmail());
        softAssertions.assertThat(response.getDateOfBirth()).isEqualTo(person.getDateOfBirth());
        softAssertions.assertThat(response.getSalary()).isEqualTo(person.getSalary());
        softAssertions.assertAll();
    }
}

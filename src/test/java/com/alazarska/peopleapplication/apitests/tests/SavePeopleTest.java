package com.alazarska.peopleapplication.apitests.tests;

import com.alazarska.peopleapplication.apitests.model.Person;
import com.alazarska.peopleapplication.apitests.model.ValidationError;
import com.alazarska.peopleapplication.apitests.model.ValidationErrorsResponse;
import com.alazarska.peopleapplication.apitests.utils.CommonAssertions;
import com.alazarska.peopleapplication.apitests.utils.MyHttpResponse;
import com.alazarska.peopleapplication.apitests.utils.PeopleApplicationClient;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class SavePeopleTest {

    private final PeopleApplicationClient peopleApplicationClient = new PeopleApplicationClient();

    @Test
    public void shouldAllowToSavePersonWithValidData() {
        Person newPerson = new Person(
                "Tom", "Smith", "test@test.com", "1996-01-01", 1000.50F
        );
        MyHttpResponse<Person> saveResponse = peopleApplicationClient.savePersonWithValidData(newPerson);
        Person createdPerson = saveResponse.getBody();

        assertThat(saveResponse.getStatusCode()).isEqualTo(200);
        CommonAssertions.assertSamePeople(createdPerson, newPerson);

        MyHttpResponse<Person> getResponse = peopleApplicationClient.getPersonById(createdPerson.getId());

        assertThat(getResponse.getStatusCode()).isEqualTo(200);
        CommonAssertions.assertExpectedPersonDataInHttpResponse(getResponse, newPerson);
    }

    @Test
    public void shouldNotAllowToSavePersonWithEmptyData() {
        MyHttpResponse<ValidationErrorsResponse> response = peopleApplicationClient.savePersonWithInvalidData(new Person());

        assertThat(response.getStatusCode()).isEqualTo(400);
        assertThat(response.getBody().getErrors()).contains(
                new ValidationError("firstName", "First Name can not be empty"),
                new ValidationError("lastName", "Last Name can not be empty"),
                new ValidationError("email", "The email address is required"),
                new ValidationError("dob", "Date of birth can not be empty"),
                new ValidationError("salary", "Salary can not be empty")
        );
    }

    @Test
    public void shouldNotAllowToSavePersonWithDateOfBirthInFuture() {
        Person newPerson = new Person(
                "Tom", "Smith", "test@test.com", "3000-01-01", 1000.00F
        );

        MyHttpResponse<ValidationErrorsResponse> response = peopleApplicationClient.savePersonWithInvalidData(newPerson);

        assertThat(response.getStatusCode()).isEqualTo(400);
        assertThat(response.getBody().getErrors()).contains(
                new ValidationError("dob", "Date of birth must be in past"));
    }

    @Test
    public void shouldNotAllowToSavePersonWithEmailWithInvalidFormat() {
        Person newPerson = new Person(
                "Tom", "Smith", "aa", "2000-06-06", 1000.00F
        );

        MyHttpResponse<ValidationErrorsResponse> response = peopleApplicationClient.savePersonWithInvalidData(newPerson);

        assertThat(response.getStatusCode()).isEqualTo(400);
        assertThat(response.getBody().getErrors()).contains(
                new ValidationError("email", "Email must be valid"));
    }
}
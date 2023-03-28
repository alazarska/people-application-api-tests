package com.alazarska.peopleapplication.apitests.tests;

import com.alazarska.peopleapplication.apitests.model.Person;
import com.alazarska.peopleapplication.apitests.model.ValidationError;
import com.alazarska.peopleapplication.apitests.model.ValidationErrorsResponse;
import com.alazarska.peopleapplication.apitests.utils.CommonAssertions;
import com.alazarska.peopleapplication.apitests.utils.CommonOperations;
import com.alazarska.peopleapplication.apitests.utils.MyHttpResponse;
import com.alazarska.peopleapplication.apitests.utils.PeopleApplicationClient;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class UpdatePersonTest {

    private final PeopleApplicationClient peopleApplicationClient = new PeopleApplicationClient();

    @Test
    public void shouldAllowToUpdatePersonWithValidData() {
        Person initialPerson = CommonOperations.createInitialPerson();

        Person updatedPerson = new Person(
                initialPerson.getId(), "Thomas", "New", "thomas.new@test.com", "1996-01-01", 2000.00F
        );

        MyHttpResponse<Person> response = peopleApplicationClient.updatePerson(updatedPerson);

        assertThat(response.getStatusCode()).isEqualTo(200);
        CommonAssertions.assertExpectedPersonDataInHttpResponse(response, updatedPerson);
    }

    @Test
    public void shouldNotAllowToUpdatePersonWithEmptyData() {
        Person initialPerson = CommonOperations.createInitialPerson();

        Person updatedPerson = initialPerson.withId(initialPerson.getId())
                .withFirstName("")
                .withLastName("")
                .withEmail("")
                .withDateOfBirth("")
                .withSalary(null);

        MyHttpResponse<ValidationErrorsResponse> response = peopleApplicationClient.updatePersonWithInvalidData(updatedPerson);

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
        Person initialPerson = CommonOperations.createInitialPerson();

        Person updatedPerson = initialPerson.withId(initialPerson.getId())
                .withDateOfBirth("3000-06-06");

        MyHttpResponse<ValidationErrorsResponse> response = peopleApplicationClient.updatePersonWithInvalidData(updatedPerson);

        assertThat(response.getStatusCode()).isEqualTo(400);
        assertThat(response.getBody().getErrors()).contains(
                new ValidationError("dob", "Date of birth must be in past")
        );
    }

    @Test
    public void shouldNotAllowToSavePersonWithEmailWithInvalidFormat() {
        Person initialPerson = CommonOperations.createInitialPerson();

        Person updatedPerson = initialPerson.withId(initialPerson.getId())
                .withEmail("test");

        MyHttpResponse<ValidationErrorsResponse> response = peopleApplicationClient.updatePersonWithInvalidData(updatedPerson);

        assertThat(response.getStatusCode()).isEqualTo(400);
        assertThat(response.getBody().getErrors()).contains(
                new ValidationError("email", "Email must be valid")
        );
    }
}
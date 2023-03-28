package com.alazarska.peopleapplication.apitests.tests;

import com.alazarska.peopleapplication.apitests.model.Person;
import com.alazarska.peopleapplication.apitests.utils.CommonOperations;
import com.alazarska.peopleapplication.apitests.utils.MyHttpResponse;
import com.alazarska.peopleapplication.apitests.utils.PeopleApplicationClient;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class DeletePersonTest {

    private final PeopleApplicationClient peopleApplicationClient = new PeopleApplicationClient();

    @Test
    public void shouldAllowToDeletePersonWhichExist() {
        Person initialPerson = CommonOperations.createInitialPerson();
        int personId = initialPerson.getId();

        MyHttpResponse<Void> deleteResponse = peopleApplicationClient.deletePerson(personId);

        assertThat(deleteResponse.getStatusCode()).isEqualTo(200);
        assertThat(peopleApplicationClient.getPersonByIdWhichNotExist(personId).getStatusCode()).isEqualTo(404);
    }

    @Test
    public void shouldReturnNotFoundStatusCodeWhenPersonWithIdWhichNotExistsIsDeleted() {
        MyHttpResponse<Void> deleteResponse = peopleApplicationClient.deletePerson(0);

        assertThat(deleteResponse.getStatusCode()).isEqualTo(404);
    }
}

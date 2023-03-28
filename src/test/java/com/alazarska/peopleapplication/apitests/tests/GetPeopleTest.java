package com.alazarska.peopleapplication.apitests.tests;

import com.alazarska.peopleapplication.apitests.model.Person;
import com.alazarska.peopleapplication.apitests.utils.CommonAssertions;
import com.alazarska.peopleapplication.apitests.utils.CommonOperations;
import com.alazarska.peopleapplication.apitests.utils.MyHttpResponse;
import com.alazarska.peopleapplication.apitests.utils.PeopleApplicationClient;
import org.testng.annotations.Test;

import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

public class GetPeopleTest {

    private final PeopleApplicationClient peopleApplicationClient = new PeopleApplicationClient();

    @Test
    public void shouldAllowToGetPeopleList() {
        Person p1 = CommonOperations.createInitialPerson();
        Person p2 = CommonOperations.createInitialPerson();
        Person p3 = CommonOperations.createInitialPerson();

        MyHttpResponse<List<Person>> response = peopleApplicationClient.getPeople();

        assertThat(response.getStatusCode()).isEqualTo(200);
        Stream.of(p1, p2, p3).forEach(person -> {
            Person personFromList = response.getBody().stream()
                    .filter(it -> it.getId().equals(person.getId()))
                    .findFirst()
                    .get();

            CommonAssertions.assertSamePeople(personFromList, person);
        });
    }

    @Test
    public void shouldAllowToGetPersonWithId() {
        Person initialPerson = CommonOperations.createInitialPerson();

        MyHttpResponse<Person> response = peopleApplicationClient.getPersonById(initialPerson.getId());

        assertThat(response.getStatusCode()).isEqualTo(200);
        CommonAssertions.assertExpectedPersonDataInHttpResponse(response, initialPerson);
    }

    @Test
    public void shouldReturnNotFoundStatusCodeWhenPersonWithIdWhichNotExistsIsSearchedFor() {
        MyHttpResponse<Void> response = peopleApplicationClient.getPersonByIdWhichNotExist(0);

        assertThat(response.getStatusCode()).isEqualTo(404);
    }
}


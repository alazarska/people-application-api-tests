package com.alazarska.peopleapplication.apitests.utils;

import com.alazarska.peopleapplication.apitests.model.Person;
import com.github.javafaker.Faker;

import java.text.SimpleDateFormat;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class CommonOperations {

    private static final PeopleApplicationClient peopleApplicationClient = new PeopleApplicationClient();
    private static final Faker faker = new Faker();
    private static final Random random = new Random();
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public static Person createInitialPerson() {
        Person initialPerson = new Person(
                faker.name().firstName(),
                faker.name().lastName(),
                faker.internet().emailAddress(),
                dateFormat.format(faker.date().past(50 * 365, TimeUnit.DAYS)),
                (float) random.nextInt(1000, 10000)
        );

       return peopleApplicationClient.savePersonWithValidData(initialPerson).getBody();
    }
}

package com.alazarska.peopleapplication.apitests.utils;

import com.alazarska.peopleapplication.apitests.model.Person;
import com.alazarska.peopleapplication.apitests.model.ValidationErrorsResponse;
import com.fasterxml.jackson.core.type.TypeReference;

import java.net.URI;
import java.net.http.HttpRequest;
import java.util.List;

public class PeopleApplicationClient {

    public MyHttpResponse<List<Person>> getPeople() {
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(buildPeopleListUrl()))
                .build();

        return HttpRequestExecutor.executeRequest(request, new TypeReference<>() {
        });
    }

    public MyHttpResponse<Person> getPersonById(int personId) {
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(buildPersonUrl(personId)))
                .build();
        return HttpRequestExecutor.executeRequest(request, new TypeReference<>() {
        });
    }

    public MyHttpResponse<Void> getPersonByIdWhichNotExist(int personId) {
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(buildPersonUrl(personId)))
                .build();
        return HttpRequestExecutor.executeRequest(request, new TypeReference<>() {
        });
    }

    public MyHttpResponse<Person> savePersonWithValidData(Person person) {
        HttpRequest request = HttpRequest.newBuilder()
                .header("Content-Type", "application/json")
                .POST(BodyPublisherBuilder.publisher(person))
                .uri(URI.create(buildPeopleListUrl()))
                .build();
        return HttpRequestExecutor.executeRequest(request, new TypeReference<>() {
        });
    }

    public MyHttpResponse<ValidationErrorsResponse> savePersonWithInvalidData(Person person) {
        HttpRequest request = HttpRequest.newBuilder()
                .header("Content-Type", "application/json")
                .POST(BodyPublisherBuilder.publisher(person))
                .uri(URI.create(buildPeopleListUrl()))
                .build();
        return HttpRequestExecutor.executeRequest(request, new TypeReference<>() {
        });
    }

    public MyHttpResponse<Person> updatePerson(Person person) {
        HttpRequest request = HttpRequest.newBuilder()
                .header("Content-Type", "application/json")
                .PUT(BodyPublisherBuilder.publisher(person))
                .uri(URI.create(buildPersonUrl(person.getId())))
                .build();
        return HttpRequestExecutor.executeRequest(request, new TypeReference<>() {
        });
    }

    public MyHttpResponse<ValidationErrorsResponse> updatePersonWithInvalidData(Person person) {
        HttpRequest request = HttpRequest.newBuilder()
                .header("Content-Type", "application/json")
                .PUT(BodyPublisherBuilder.publisher(person))
                .uri(URI.create(buildPersonUrl(person.getId())))
                .build();
        return HttpRequestExecutor.executeRequest(request, new TypeReference<>() {
        });
    }

    public MyHttpResponse<Void> deletePerson(int personId) {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(buildPersonUrl(personId)))
                .header("Content-Type", "application/json")
                .DELETE()
                .build();
        return HttpRequestExecutor.executeRequest(request, new TypeReference<>() {
        });
    }

    private String buildPeopleListUrl() {
        return buildApplicationUrl();
    }

    private String buildPersonUrl(int personId) {
        return buildApplicationUrl() + "/" + personId;
    }

    private String buildApplicationUrl() {
        return TestConfiguration.applicationUrl + "/api/people";
    }
}
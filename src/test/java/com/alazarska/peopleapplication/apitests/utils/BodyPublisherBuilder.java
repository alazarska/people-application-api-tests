package com.alazarska.peopleapplication.apitests.utils;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;

import java.net.http.HttpRequest;

public class BodyPublisherBuilder {

    private final static ObjectMapper objectMapper = new ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    @SneakyThrows
    public static HttpRequest.BodyPublisher publisher(Object obj) {
        return HttpRequest.BodyPublishers.ofString(objectMapper.writeValueAsString(obj));
    }
}

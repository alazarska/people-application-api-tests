package com.alazarska.peopleapplication.apitests.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;

import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class HttpRequestExecutor {

    private final static ObjectMapper objectMapper = new ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    private final static HttpClient client = HttpClient.newBuilder().build();

    @SneakyThrows
    public static <T> MyHttpResponse<T> executeRequest(HttpRequest request, TypeReference<T> typeReference) {
        System.out.println("Executing request to " + request.method() + " " + request.uri());
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println(
                "Request to " + request.method() + " " + request.uri() + " returned code " +
                        response.statusCode() + " with body " + response.body()
        );
        String typeName = typeReference.getType().getTypeName();
        T body = null;
        if (!typeName.equals("java.lang.Void")) {
            body = objectMapper.readValue(response.body(), typeReference);
        }
        return new MyHttpResponse<>(
                response.statusCode(),
                response.headers(),
                response.body(),
                body
        );
    }
}

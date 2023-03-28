package com.alazarska.peopleapplication.apitests.utils;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.net.http.HttpHeaders;

@Data
@AllArgsConstructor
public class MyHttpResponse<T> {

    private int statusCode;
    private HttpHeaders headers;
    private String rawBody;
    private T body;
}

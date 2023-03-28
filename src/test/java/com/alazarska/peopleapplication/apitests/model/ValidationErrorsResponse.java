package com.alazarska.peopleapplication.apitests.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ValidationErrorsResponse {
    private List<ValidationError> errors;
}

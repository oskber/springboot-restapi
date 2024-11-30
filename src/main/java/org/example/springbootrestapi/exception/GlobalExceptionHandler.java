package org.example.springbootrestapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.net.URI;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(LocationNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ProblemDetail handleLocationNotFoundException(LocationNotFoundException ex) {
        ProblemDetail problemDetails = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, ex.getLocalizedMessage());
        problemDetails.setType(URI.create("http://localhost:8080/errors/location-not-found"));
        problemDetails.setTitle("Location Not Found");
        problemDetails.setProperty("locationId", ex.getId());
        return problemDetails;
    }

    @ExceptionHandler(CategoryAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ProblemDetail handleCategoryAlreadyExistsException(CategoryAlreadyExistsException ex) {
        ProblemDetail problemDetails = ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT, ex.getLocalizedMessage());
        problemDetails.setType(URI.create("http://localhost:8080/errors/category-already-exists"));
        problemDetails.setTitle("Category Already Exists");
        return problemDetails;
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ProblemDetail handleIllegalArgumentException(IllegalArgumentException ex) {
        ProblemDetail problemDetails = ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT, ex.getLocalizedMessage());
        problemDetails.setType(URI.create("http://localhost:8080/errors/illegal-argument"));
        problemDetails.setTitle("Illegal Argument");
        return problemDetails;
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ProblemDetail handleException(Exception ex) {
        ProblemDetail problemDetails = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage());
        problemDetails.setType(URI.create("http://localhost:8080/errors/general-error"));
        problemDetails.setTitle("General Error");
        return problemDetails;
    }
}
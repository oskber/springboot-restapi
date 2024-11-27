package org.example.springbootrestapi.exception;

import lombok.Getter;

@Getter
public class LocationNotFoundException extends RuntimeException {
    private final int id;
    public LocationNotFoundException(int id) {
        super("Location not found with id: " + id);
        this.id = id;
    }
}

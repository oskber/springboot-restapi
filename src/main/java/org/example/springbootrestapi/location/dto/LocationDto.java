package org.example.springbootrestapi.location.dto;

import org.example.springbootrestapi.entity.LocationEntity;

public record LocationDto(String name, String description, String categoryName, Boolean status) {
    public static LocationDto fromLocation(LocationEntity location) {
        return new LocationDto(location.getName(),
                location.getDescription(),
                location.getCategory().getName(),
                location.getStatus());
    }
}

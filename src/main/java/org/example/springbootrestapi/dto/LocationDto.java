package org.example.springbootrestapi.dto;

import jakarta.validation.constraints.NotNull;
import org.example.springbootrestapi.entity.LocationEntity;

import java.util.List;

public record LocationDto(@NotNull String name, String description, @NotNull String categoryName, Boolean status, List<Double> coordinates, Boolean deleted, @NotNull Integer userId) {

    public static LocationDto fromLocation(LocationEntity location) {
        return new LocationDto(location.getName(),
                location.getDescription(),
                location.getCategory().getName(),
                location.getStatus(),
                List.of(location.getCoordinate().getPosition().getLon(), location.getCoordinate().getPosition().getLat()),
                location.getDeleted(),
                location.getUserId()
        );
    }
}

package org.example.springbootrestapi.dto;

import jakarta.validation.constraints.NotNull;
import org.example.springbootrestapi.entity.LocationEntity;
import org.geolatte.geom.G2D;
import org.geolatte.geom.Point;

public record LocationDto(@NotNull String name, String description, @NotNull String categoryName, Boolean status, Point<G2D> coordinate, Boolean deleted, @NotNull Integer userId) {

    public static LocationDto fromLocation(LocationEntity location) {
        return new LocationDto(location.getName(),
                location.getDescription(),
                location.getCategory().getName(),
                location.getStatus(),
                location.getCoordinate(),
                location.getDeleted(),
                location.getUserId()
        );
    }
    public float getLat() {
        return (float) coordinate.getPosition().getLat();
    }

    public float getLon() {
        return (float) coordinate.getPosition().getLon();
    }
}

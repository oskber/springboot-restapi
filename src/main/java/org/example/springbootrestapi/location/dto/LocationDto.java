package org.example.springbootrestapi.location.dto;

import org.example.springbootrestapi.entity.LocationEntity;
import org.geolatte.geom.G2D;
import org.geolatte.geom.Point;

public record LocationDto(String name, String description, String categoryName, Boolean status, Point<G2D> coordinate, Boolean deleted) {
    public static LocationDto fromLocation(LocationEntity location) {
        return new LocationDto(location.getName(),
                location.getDescription(),
                location.getCategory().getName(),
                location.getStatus(),
                location.getCoordinate(),
                location.getDeleted()
        );
    }


}

package org.example.springbootrestapi.location.valueobject;

import org.example.springbootrestapi.location.dto.LocationDto;
import org.geolatte.geom.G2D;
import org.geolatte.geom.Geometries;
import org.geolatte.geom.Point;
import org.geolatte.geom.builder.DSL;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.geolatte.geom.crs.CoordinateReferenceSystems.WGS84;

@RestController
public class LocationController {
    LocationService locationService;

    public LocationController(LocationService locationService) {
        this.locationService = locationService;
    }

    @GetMapping("/locations")
    public List<LocationDto> getAllPublicLocations() {
        return locationService.getAllPublicLocations();
    }

    @GetMapping("/locations/{id}")
    public LocationDto getPublicLocationById(@PathVariable int id) {
        return locationService.getPublicLocationById(id);
    }

    @GetMapping("/locations/category/{categoryName}")
    public List<LocationDto> getPublicLocationsByCategoryId(@PathVariable String categoryName) {
        return locationService.getPublicLocationsByCategoryId(categoryName);
    }

    @GetMapping("/locations/user")
    public List<LocationDto> getLocationsByAuthenticatedUser() {
        return locationService.getLocationsByAuthenticatedUser();
    }

    @PutMapping("/locations/{id}")
    public ResponseEntity<Void> updateLocation(@PathVariable int id, @Validated @RequestBody LocationDto locationDto) {
        locationService.updateLocation(id, locationDto);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/locations/area")
    public List<LocationDto> getLocationsWithinRadius(@RequestParam double lon, @RequestParam double lat, @RequestParam double radius) {
        return locationService.getLocationsWithinRadius(lon, lat, radius);

    }
}

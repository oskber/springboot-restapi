package org.example.springbootrestapi.controller;

import org.example.springbootrestapi.dto.LocationDto;
import org.example.springbootrestapi.entity.LocationEntity;
import org.example.springbootrestapi.service.LocationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

import java.util.List;

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
        return locationService.getPublicLocationsByCategoryName(categoryName);
    }

    @GetMapping("/locations/user/all")
    public List<LocationDto> getAllLocationsByAuthenticatedUser() {
        return locationService.getAllLocationsByAuthenticatedUser();
    }

    @PostMapping("/locations")
    public ResponseEntity<Void> createLocation(@RequestBody LocationDto locationDto) {
        LocationEntity location = locationService.createLocation(locationDto);
        if (location == null) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        URI locationUri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(location.getId())
                .toUri();
        return ResponseEntity.created(locationUri).build();
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

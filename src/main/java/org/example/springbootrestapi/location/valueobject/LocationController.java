package org.example.springbootrestapi.location.valueobject;

import org.example.springbootrestapi.location.dto.LocationDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

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
        return locationService.getPublicLocationsByCategoryId(categoryName);
    }

}

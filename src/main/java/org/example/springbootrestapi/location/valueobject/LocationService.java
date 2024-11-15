package org.example.springbootrestapi.location.valueobject;

import org.example.springbootrestapi.location.dto.LocationDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LocationService {
    private final LocationRepository locationRepository;

    public LocationService(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }

    public List<LocationDto> getAllPublicLocations() {
        return locationRepository.findByStatusTrue().stream()
                .map(LocationDto::fromLocation)
                .toList();
    }


    public LocationDto getPublicLocationById(int id) {
        return locationRepository.findById(id)
                .map(LocationDto::fromLocation)
                .orElseThrow(() -> new IllegalArgumentException("Location not found"));
    }

    public List<LocationDto> getPublicLocationsByCategoryId(String categoryName) {
        return locationRepository.findByStatusTrueAndCategoryName(categoryName).stream()
                .map(LocationDto::fromLocation)
                .toList();
    }
}

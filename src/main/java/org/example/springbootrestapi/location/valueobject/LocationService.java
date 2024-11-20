package org.example.springbootrestapi.location.valueobject;

import org.example.springbootrestapi.category.valueobject.CategoryRepository;
import org.example.springbootrestapi.entity.CategoryEntity;
import org.example.springbootrestapi.entity.LocationEntity;
import org.example.springbootrestapi.location.dto.LocationDto;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LocationService {
    private final LocationRepository locationRepository;
    private final CategoryRepository categoryRepository;

    public LocationService(LocationRepository locationRepository, CategoryRepository categoryRepository) {
        this.locationRepository = locationRepository;
        this.categoryRepository = categoryRepository;
    }

    public List<LocationDto> getAllPublicLocations() {
        return locationRepository.findByStatusTrueAndDeletedFalse().stream()
                .map(LocationDto::fromLocation)
                .toList();
    }


    public LocationDto getPublicLocationById(int id) {
        return locationRepository.findById(id)
                .map(LocationDto::fromLocation)
                .orElseThrow(() -> new IllegalArgumentException("Location not found"));
    }

    public List<LocationDto> getPublicLocationsByCategoryId(String categoryName) {
        return locationRepository.findByStatusTrueAndCategoryNameAndDeletedFalse(categoryName).stream()
                .map(LocationDto::fromLocation)
                .toList();
    }

    public List<LocationDto> getLocationsByAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        int userId = getUserIdByUsername(username);
        return locationRepository.findByUserIdAndDeletedFalse(userId).stream()
                .map(LocationDto::fromLocation)
                .toList();
    }

    private int getUserIdByUsername(String username) {
        return 1;
    }

    //Update and soft delete location
    public void updateLocation(int id, LocationDto locationDto) {
        LocationEntity location = locationRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Location not found"));

        if (locationDto.name() == null) {
            throw new IllegalArgumentException("Location name must not be null");
        }

        CategoryEntity category = categoryRepository.findByName(locationDto.categoryName())
                .orElseThrow(() -> new IllegalArgumentException("Category not found"));

        if (locationDto.deleted() != null) {
            location.setDeleted(locationDto.deleted());
        }

        location.setName(locationDto.name());
        location.setDescription(locationDto.description());
        location.setCategory(category);
        location.setStatus(locationDto.status());
        locationRepository.save(location);
    }

    public List<LocationDto> getLocationsWithinRadius(double lon, double lat, double radius) {
        return locationRepository.findAllWithinRadius(lon, lat, radius).stream()
                .map(LocationDto::fromLocation)
                .toList();
    }
}

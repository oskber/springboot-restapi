package org.example.springbootrestapi.service;

import org.example.springbootrestapi.repository.CategoryRepository;
import org.example.springbootrestapi.entity.CategoryEntity;
import org.example.springbootrestapi.entity.LocationEntity;
import org.example.springbootrestapi.dto.LocationDto;
import org.example.springbootrestapi.repository.LocationRepository;
import org.geolatte.geom.G2D;
import org.geolatte.geom.Geometries;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import java.util.List;

import static org.geolatte.geom.crs.CoordinateReferenceSystems.WGS84;

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

    public LocationEntity createLocation(LocationDto locationDto) {
        if (locationDto.coordinates().size() != 2) {
            throw new IllegalArgumentException("Invalid coordinates");
        }

        double lon = locationDto.coordinates().get(0);
        double lat = locationDto.coordinates().get(1);

        if (lat < -90 || lat > 90 || lon < -180 || lon > 180) {
            throw new IllegalArgumentException("Invalid latitude or longitude");
        }

        LocationEntity location = new LocationEntity();
        var geo = Geometries.mkPoint(new G2D(lon, lat), WGS84);
        location.setCoordinate(geo);
        location.setName(locationDto.name());
        location.setDescription(locationDto.description());
        location.setUserId(locationDto.userId());
        location.setStatus(locationDto.status());
        location.setDeleted(locationDto.deleted());

        CategoryEntity category = categoryRepository.findByName(locationDto.categoryName())
                .orElseThrow(() -> new IllegalArgumentException("Category not found"));
        location.setCategory(category);

        return locationRepository.save(location);
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

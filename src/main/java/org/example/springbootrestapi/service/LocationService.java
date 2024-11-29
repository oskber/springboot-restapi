package org.example.springbootrestapi.service;

import lombok.extern.slf4j.Slf4j;
import com.google.common.util.concurrent.RateLimiter;
import org.example.springbootrestapi.exception.LocationNotFoundException;
import org.example.springbootrestapi.repository.CategoryRepository;
import org.example.springbootrestapi.entity.CategoryEntity;
import org.example.springbootrestapi.entity.LocationEntity;
import org.example.springbootrestapi.dto.LocationDto;
import org.example.springbootrestapi.repository.LocationRepository;
import org.geolatte.geom.G2D;
import org.geolatte.geom.Geometries;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.retry.annotation.Retryable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientResponseException;

import java.util.List;
import java.util.Locale;

import static org.geolatte.geom.crs.CoordinateReferenceSystems.WGS84;

@Service
@Slf4j
public class LocationService {
    private final LocationRepository locationRepository;
    private final CategoryRepository categoryRepository;
    private final RestClient restClient;
    private final RateLimiter rateLimiter = RateLimiter.create(1.0);

    public LocationService(LocationRepository locationRepository, CategoryRepository categoryRepository, RestClient restClient) {
        this.locationRepository = locationRepository;
        this.categoryRepository = categoryRepository;
        this.restClient = restClient;
    }

    @Cacheable("address")
    public String getAddressFromCoordinates(double lat, double lon) {
        String apiKey = "674585f2b5a1c082814115nrj37d824";
        String url = String.format(Locale.US, "https://geocode.maps.co/reverse?lat=%.7f&lon=%.7f&api_key=%s", lat, lon, apiKey);

        rateLimiter.acquire();

        try {
            log.info("Fetching address from URL: {}", url);
            String response = restClient.get().uri(url).retrieve().body(String.class);
            log.info("Received response: {}", response);
            return response;
        } catch (RestClientResponseException e) {
            log.error("Error while fetching address from coordinates", e);
            return "Address not found";
        }
    }

    @Cacheable("locations")
    public List<LocationDto> getAllPublicLocations() {
        return locationRepository.findByStatusTrueAndDeletedFalse().stream().map(LocationDto::fromLocation).toList();
    }

    @Cacheable("locations")
    public LocationDto getPublicLocationById(int id) {
        return locationRepository.findById(id).map(location -> {
            G2D position = location.getCoordinate().getPosition();
            double lat = position.getLat();
            double lon = position.getLon();
            String address = getAddressFromCoordinates(lon, lat);
            location.setAddress(address);
            return LocationDto.fromLocation(location);
        }).orElseThrow(() -> {
            log.error("Location not found with id: {}", id);
            return new LocationNotFoundException(id);
        });
    }

    @Cacheable("locations")
    public List<LocationDto> getPublicLocationsByCategoryId(String categoryName) {
        return locationRepository.findByStatusTrueAndCategoryNameAndDeletedFalse(categoryName).stream().map(LocationDto::fromLocation).toList();
    }

    public List<LocationDto> getLocationsByAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        int userId = getUserIdByUsername(username);
        return locationRepository.findByUserIdAndDeletedFalse(userId).stream().map(LocationDto::fromLocation).toList();
    }

    private int getUserIdByUsername(String username) {
        return 1;
    }

    @Retryable(maxAttempts = 2)
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

        CategoryEntity category = categoryRepository.findByName(locationDto.categoryName()).orElseThrow(() -> new IllegalArgumentException("Category not found"));
        location.setCategory(category);

        return locationRepository.save(location);
    }

    @Retryable(maxAttempts = 2)
    //Update and soft delete location
    public void updateLocation(int id, LocationDto locationDto) {
        LocationEntity location = locationRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Location not found"));

        if (locationDto.name() == null) {
            throw new IllegalArgumentException("Location name must not be null");
        }

        CategoryEntity category = categoryRepository.findByName(locationDto.categoryName()).orElseThrow(() -> new IllegalArgumentException("Category not found"));

        if (locationDto.deleted() != null) {
            location.setDeleted(locationDto.deleted());
        }

        location.setName(locationDto.name());
        location.setDescription(locationDto.description());
        location.setCategory(category);
        location.setStatus(locationDto.status());
        locationRepository.save(location);
    }

    @Cacheable("locations")
    @Retryable(maxAttempts = 2)
    public List<LocationDto> getLocationsWithinRadius(double lon, double lat, double radius) {
        return locationRepository.findAllWithinRadius(lon, lat, radius).stream().map(LocationDto::fromLocation).toList();
    }
}

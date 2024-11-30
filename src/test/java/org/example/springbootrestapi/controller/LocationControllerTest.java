package org.example.springbootrestapi.controller;

import org.example.springbootrestapi.dto.LocationDto;
import org.example.springbootrestapi.entity.CategoryEntity;
import org.example.springbootrestapi.entity.LocationEntity;
import org.example.springbootrestapi.service.LocationService;
import org.geolatte.geom.G2D;
import org.geolatte.geom.Geometries;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.geolatte.geom.crs.CoordinateReferenceSystems.WGS84;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(LocationController.class)
//@AutoConfigureMockMvc(addFilters = false) //Disable security
@EnableMethodSecurity
class LocationControllerTest {

    @MockBean
    LocationService locationService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    void getAllPublicLocations() throws Exception {
        mockMvc.perform(get("/locations"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("[]"));
    }

    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    void getLocationById() throws Exception {
        mockMvc.perform(get("/locations/1"))
                .andExpect(status().isOk());
    }


    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    void createLocation() throws Exception {
        String locationJson = """
    {
      "name": "test",
      "description": "A major test intersection in New test City",
      "categoryName": "Squares",
      "status": false,
      "coordinates": [
        -73.98513,
        40.758896
      ],
      "deleted": false,
      "userId": 4
    }
    """;

        LocationEntity mockLocation = new LocationEntity();
        mockLocation.setId(1);
        mockLocation.setName("test");
        mockLocation.setDescription("A major test intersection in New test City");
        mockLocation.setUserId(4);
        mockLocation.setStatus(false);
        mockLocation.setDeleted(false);
        mockLocation.setCoordinate(Geometries.mkPoint(new G2D(-73.98513, 40.758896), WGS84));
        CategoryEntity category = new CategoryEntity();
        category.setName("Squares");
        mockLocation.setCategory(category);

        when(locationService.createLocation(any(LocationDto.class))).thenReturn(mockLocation);

        mockMvc.perform(post("/locations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(locationJson)
                        .with(csrf()))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"));
    }

    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    void getAllLocationsByAuthenticatedUser() throws Exception {
        LocationEntity location1 = new LocationEntity();
        CategoryEntity category = new CategoryEntity();
        category.setName("Museums");
        location1.setId(1);
        location1.setName("Location 1");
        location1.setCategory(category);
        location1.setDescription("Description 1");
        location1.setUserId(1);
        location1.setStatus(true);
        location1.setDeleted(false);
        location1.setCoordinate(Geometries.mkPoint(new G2D(-73.98513, 40.758896), WGS84));

        LocationEntity location2 = new LocationEntity();
        location2.setId(2);
        location2.setName("Location 2");
        location2.setCategory(category);
        location2.setDescription("Description 2");
        location2.setUserId(1);
        location2.setStatus(false);
        location2.setDeleted(false);
        location2.setCoordinate(Geometries.mkPoint(new G2D(-73.98513, 40.758896), WGS84));

        List<LocationDto> locations = List.of(LocationDto.fromLocation(location1), LocationDto.fromLocation(location2));

        when(locationService.getAllLocationsByAuthenticatedUser()).thenReturn(locations);

        mockMvc.perform(get("/locations/user/all"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("""
                        [
                            {
                                "id": 1,
                                "name": "Location 1",
                                "description": "Description 1",
                                "coordinates": [-73.98513, 40.758896],
                                "status": true,
                                "deleted": false,
                                "userId": 1,
                                "categoryName": "Museums"
                            },
                            {
                                "id": 2,
                                "name": "Location 2",
                                "description": "Description 2",
                                "coordinates": [-73.98513, 40.758896],
                                "status": false,
                                "deleted": false,
                                "userId": 1,
                                "categoryName": "Museums"
                            }
                        ]
                        """));
    }
}
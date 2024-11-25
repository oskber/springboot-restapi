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
        mockLocation.setId(1); // Set a valid ID
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
}
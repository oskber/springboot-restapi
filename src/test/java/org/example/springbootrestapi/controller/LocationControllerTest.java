package org.example.springbootrestapi.controller;

import org.example.springbootrestapi.service.LocationService;
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
    void getPrivateLocation() throws Exception {
        mockMvc.perform(get("/locations/1"))
                .andExpect(status().isOk());
    }


//    @Test
//    @WithMockUser(username = "user", roles = {"USER"})
//    void createLocation() throws Exception {
//        String locationJson = """
//    {
//      "name": "test",
//      "description": "A major test intersection in New test City",
//      "categoryName": "Squares",
//      "status": false,
//      "coordinate": {
//        "type": "Point",
//        "crs": {
//          "type": "name",
//          "properties": {
//            "name": "EPSG:4326"
//          }
//        },
//        "coordinates": [
//          40.758896,
//          -73.98513
//        ]
//      },
//      "deleted": false,
//      "userId": 4
//    }
//    """;
//
//        mockMvc.perform(post("/locations")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(locationJson)
//                        .with(csrf()))
//                .andExpect(status().isOk());
//    }
}
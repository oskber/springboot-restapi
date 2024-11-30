package org.example.springbootrestapi.controller;

import org.example.springbootrestapi.dto.CategoryDto;
import org.example.springbootrestapi.service.CategoryService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


//@AutoConfigureMockMvc(addFilters = false) //Disable security
@WebMvcTest(CategoryController.class)
class CategoryControllerTest {

    @MockBean
    CategoryService categoryService;

    @Autowired
    MockMvc mvc;


    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void addCategory_withAdminRole_shouldReturnCreated() throws Exception {
        CategoryDto categoryDto = new CategoryDto("Museums", "M", "Description of Museums");

        when(categoryService.addCategory(any(CategoryDto.class))).thenReturn(1);

        mockMvc.perform(post("/categories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                        {
                            "name": "Museums",
                            "symbol": "M",
                            "description": "Description of Museums"
                        }
                        """)
                        .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(status().isCreated());
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void addCategory_withExistingCategoryName_shouldReturnConflict() throws Exception {
        when(categoryService.addCategory(any(CategoryDto.class))).thenThrow(new IllegalArgumentException("Category with the same name already exists"));

        mockMvc.perform(post("/categories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                        {
                            "name": "Museums",
                            "symbol": "M",
                            "description": "Description of Museums"
                        }
                        """)
                        .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(status().isConflict());
    }
}
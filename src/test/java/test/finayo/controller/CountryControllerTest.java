package test.unicorn.studio.controller;

import com.unicorn.studio.controller.CountryController;
import com.unicorn.studio.entity.Country;
import com.unicorn.studio.service.LocationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@ExtendWith(SpringExtension.class)
@Import(CountryController.class)
@WebMvcTest(CountryController.class)
@AutoConfigureMockMvc(addFilters = false)
@ContextConfiguration(classes = {SpringBootApplication.class})
public class CountryControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private LocationService location;

    @Test
    public void shouldGetCitiesReturns200() throws Exception {
        Country country = new Country("United States","US","NA");
        List<Country> countryList = new ArrayList<>();
        countryList.add(country);

        when(location.getCountries()).thenReturn(countryList);
        location.saveCountry(country);

        mockMvc.perform(get("/api/v1/countries")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldGetCountryReturn200() throws Exception {
        Country country = new Country("United States","US","NA");

        when(location.getCountry(1L)).thenReturn(country);
        location.saveCountry(country);

        mockMvc.perform(get("/api/v1/countries/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldGetCountryReturn404() throws Exception {
        Country country = new Country();

        when(location.getCountry(1L)).thenReturn(country);

        mockMvc.perform(get("/api/v1/countries/11251")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void shouldSaveCountryReturn201() throws Exception {
        Country country = new Country("United States","US","NA");

        when(location.getCountry(1L)).thenReturn(country);

        mockMvc.perform(post("/api/v1/countries/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(country)))
                .andExpect(status().isCreated());
    }

    @Test
    public void shouldSaveCountryReturn400() throws Exception {
        Country country = new Country();

        when(location.getCountry(1L)).thenReturn(country);

        mockMvc.perform(post("/api/v1/countries/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(country)))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void shouldEditCountryReturn200() throws Exception {
        Country country = new Country("United States","US","NA");

        when(location.getCountry(1L)).thenReturn(country);
        country.setName("New York");

        mockMvc.perform(put("/api/v1/countries/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(country)))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldEditCountryReturn400() throws Exception {
        Country country = new Country();

        when(location.getCountry(1L)).thenReturn(country);

        mockMvc.perform(put("/api/v1/countries/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(country)))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void shouldDeleteCountryReturn200() throws Exception {
        Country country = new Country("United States","US","NA");

        when(location.getCountry(1L)).thenReturn(country);
        location.saveCountry(country);

        mockMvc.perform(delete("/api/v1/countries/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(country)))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldDeleteCountryReturn404() throws Exception {
        Country country = new Country();

        when(location.getCountry(1L)).thenReturn(country);

        mockMvc.perform(delete("/api/v1/countries/2")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(country)))
                .andExpect(status().is4xxClientError());
    }
}

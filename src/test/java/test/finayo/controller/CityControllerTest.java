package test.unicorn.studio.controller;

import com.unicorn.studio.controller.CityController;
import com.unicorn.studio.entity.City;
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
@Import(CityController.class)
@WebMvcTest(CityController.class)
@AutoConfigureMockMvc(addFilters = false)
@ContextConfiguration(classes = {SpringBootApplication.class})
public class CityControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private LocationService location;

    @Test
    public void shouldGetCitiesReturns200() throws Exception {
        City city = new City("San Francisco");
        List<City> cityList = new ArrayList<>();
        cityList.add(city);

        when(location.getCities()).thenReturn(cityList);
        location.saveCity(city);

        mockMvc.perform(get("/api/v1/cities")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());
    }

    @Test
    public void shouldGetCityReturn200() throws Exception {
        City city = new City("San Francisco");

        when(location.getCity(1L)).thenReturn(city);
        location.saveCity(city);

        mockMvc.perform(get("/api/v1/cities/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldGetCityReturn404() throws Exception {
        City city = new City();

        when(location.getCity(1L)).thenReturn(city);

        mockMvc.perform(get("/api/v1/cities/11251")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void shouldSaveCityReturn201() throws Exception {
        City city = new City("San Francisco");

        when(location.getCity(1L)).thenReturn(city);

        mockMvc.perform(post("/api/v1/cities/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(city)))
                .andExpect(status().isCreated());
    }

    @Test
    public void shouldSaveCityReturn400() throws Exception {
        City city = new City();
        city = null;

        when(location.getCity(1L)).thenReturn(city);

        mockMvc.perform(post("/api/v1/cities/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(city)))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void shouldEditCityReturn200() throws Exception {
        City city = new City("San Francisco");

        when(location.getCity(1L)).thenReturn(city);
        city.setName("New York");

        mockMvc.perform(put("/api/v1/cities/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(city)))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldEditCityReturn400() throws Exception {
        City city = new City();
        city = null;

        when(location.getCity(1L)).thenReturn(city);

        mockMvc.perform(put("/api/v1/cities/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(city)))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void shouldDeleteCityReturn200() throws Exception {
        City city = new City("San Francisco");

        when(location.getCity(1L)).thenReturn(city);
        location.saveCity(city);

        mockMvc.perform(delete("/api/v1/cities/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(city)))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldDeleteCityReturn404() throws Exception {
        City city = new City();

        when(location.getCity(1L)).thenReturn(city);

        mockMvc.perform(delete("/api/v1/cities/2")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(city)))
                .andExpect(status().is4xxClientError());
    }
}

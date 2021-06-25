package test.unicorn.studio.controller;

import com.unicorn.studio.controller.StateController;
import com.unicorn.studio.entity.State;
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
@Import(StateController.class)
@WebMvcTest(StateController.class)
@AutoConfigureMockMvc(addFilters = false)
@ContextConfiguration(classes = {SpringBootApplication.class})
public class StateControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private LocationService location;

    @Test
    public void shouldGetCitiesReturns200() throws Exception {
        State state = new State("California");
        List<State> stateList = new ArrayList<>();
        stateList.add(state);

        when(location.getStates()).thenReturn(stateList);
        location.saveState(state);

        mockMvc.perform(get("/api/v1/states")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldGetStateReturn200() throws Exception {
        State state = new State("California");

        when(location.getState(1L)).thenReturn(state);
        location.saveState(state);

        mockMvc.perform(get("/api/v1/states/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldGetStateReturn404() throws Exception {
        State state = new State();

        when(location.getState(1L)).thenReturn(state);

        mockMvc.perform(get("/api/v1/states/11251")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void shouldSaveStateReturn201() throws Exception {
        State state = new State("California");

        when(location.getState(1L)).thenReturn(state);

        mockMvc.perform(post("/api/v1/states/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(state)))
                .andExpect(status().isCreated());
    }

    @Test
    public void shouldSaveStateReturn400() throws Exception {
        State state = new State();
        state = null;

        when(location.getState(1L)).thenReturn(state);

        mockMvc.perform(post("/api/v1/states/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(state)))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void shouldEditStateReturn200() throws Exception {
        State state = new State("California");

        when(location.getState(1L)).thenReturn(state);
        state.setName("New York");

        mockMvc.perform(put("/api/v1/states/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(state)))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldEditStateReturn400() throws Exception {
        State state = new State();
        state = null;

        when(location.getState(1L)).thenReturn(state);

        mockMvc.perform(put("/api/v1/states/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(state)))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void shouldDeleteStateReturn200() throws Exception {
        State state = new State("California");

        when(location.getState(1L)).thenReturn(state);
        location.saveState(state);

        mockMvc.perform(delete("/api/v1/states/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(state)))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldDeleteStateReturn404() throws Exception {
        State state = new State();

        when(location.getState(1L)).thenReturn(state);

        mockMvc.perform(delete("/api/v1/states/2")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(state)))
                .andExpect(status().is4xxClientError());
    }
}

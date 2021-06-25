package test.unicorn.studio.controller;

import com.unicorn.studio.controller.IndustryController;
import com.unicorn.studio.entity.Industry;
import com.unicorn.studio.service.CompanyService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.unicorn.studio.service.UtilityService;
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
@Import(IndustryController.class)
@WebMvcTest(IndustryController.class)
@AutoConfigureMockMvc(addFilters = false)
@ContextConfiguration(classes = {SpringBootApplication.class})
public class IndustryControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UtilityService utilityService;

    @Test
    public void shouldGetCitiesReturns200() throws Exception {
        Industry industry = new Industry("Internet","Internet");
        List<Industry> industryList = new ArrayList<>();
        industryList.add(industry);

        when(utilityService.getIndustries()).thenReturn(industryList);
        utilityService.saveIndustry(industry);

        mockMvc.perform(get("/api/v1/industries")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldGetIndustryReturn200() throws Exception {
        Industry industry = new Industry("Internet","Internet");

        when(utilityService.getIndustry(1L)).thenReturn(industry);
        utilityService.saveIndustry(industry);

        mockMvc.perform(get("/api/v1/industries/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldGetIndustryReturn404() throws Exception {
        Industry industry = new Industry();

        when(utilityService.getIndustry(1L)).thenReturn(industry);

        mockMvc.perform(get("/api/v1/industries/11251")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void shouldSaveIndustryReturn201() throws Exception {
        Industry industry = new Industry("Internet","Internet");

        when(utilityService.getIndustry(1L)).thenReturn(industry);

        mockMvc.perform(post("/api/v1/industries/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(industry)))
                .andExpect(status().isCreated());
    }

    @Test
    public void shouldSaveIndustryReturn400() throws Exception {
        Industry industry = new Industry();
        industry = null;

        when(utilityService.getIndustry(1L)).thenReturn(industry);

        mockMvc.perform(post("/api/v1/industries/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(industry)))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void shouldEditIndustryReturn200() throws Exception {
        Industry industry = new Industry("Internet","Internet");

        when(utilityService.getIndustry(1L)).thenReturn(industry);
        industry.setName("Finance");

        mockMvc.perform(put("/api/v1/industries/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(industry)))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldEditIndustryReturn400() throws Exception {
        Industry industry = new Industry();
        industry = null;

        when(utilityService.getIndustry(1L)).thenReturn(industry);

        mockMvc.perform(put("/api/v1/industries/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(industry)))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void shouldDeleteIndustryReturn200() throws Exception {
        Industry industry = new Industry("Internet","Internet");

        when(utilityService.getIndustry(1L)).thenReturn(industry);
        utilityService.saveIndustry(industry);

        mockMvc.perform(delete("/api/v1/industries/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(industry)))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldDeleteIndustryReturn404() throws Exception {
        Industry industry = new Industry();

        when(utilityService.getIndustry(1L)).thenReturn(industry);

        mockMvc.perform(delete("/api/v1/industries/2")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(industry)))
                .andExpect(status().is4xxClientError());
    }
}

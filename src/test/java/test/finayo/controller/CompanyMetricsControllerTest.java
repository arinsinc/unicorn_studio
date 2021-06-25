package test.unicorn.studio.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.unicorn.studio.controller.CompanyMetricsController;
import com.unicorn.studio.entity.CompanyMetrics;
import com.unicorn.studio.projection.CompanyMetricsProjection;
import com.unicorn.studio.service.CompanyService;
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
import test.unicorn.studio.utils.InitializeData;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@Import(CompanyMetricsController.class)
@WebMvcTest(CompanyMetricsController.class)
@AutoConfigureMockMvc(addFilters = false)
@ContextConfiguration(classes = {SpringBootApplication.class})
public class CompanyMetricsControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CompanyService companyService;

    @Test
    public void shouldGetCompaniesReturns200() throws Exception {
        CompanyMetrics companyMetrics = InitializeData.initializeCompanyMetrics();
        List<CompanyMetricsProjection> companyMetricsList = new ArrayList<>();

        when(companyService.getAllCompanyMetrics("1")).thenReturn(companyMetricsList);
        companyService.saveCompanyMetrics(companyMetrics);

        mockMvc.perform(get("/api/v1/company-metrics/company_id=1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldGetCompanyMetricsReturn200() throws Exception {
        CompanyMetrics companyMetrics = InitializeData.initializeCompanyMetrics();
        CompanyMetricsProjection metrics = null;

        when(companyService.getCompanyMetrics("1")).thenReturn(metrics);
        companyService.saveCompanyMetrics(companyMetrics);

        mockMvc.perform(get("/api/v1/company-metrics/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldGetCompanyMetricsReturn404() throws Exception {
        CompanyMetricsProjection companyMetrics = null;

        when(companyService.getCompanyMetrics("1")).thenReturn(companyMetrics);

        mockMvc.perform(get("/api/v1/company-metrics/11251")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void shouldSaveCompanyMetricsReturn201() throws Exception {
        CompanyMetricsProjection companyMetrics = null;

        when(companyService.getCompanyMetrics("1")).thenReturn(companyMetrics);

        mockMvc.perform(post("/api/v1/company-metrics/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(companyMetrics)))
                .andExpect(status().isCreated());
    }

    @Test
    public void shouldSaveCompanyMetricsReturn400() throws Exception {
        CompanyMetricsProjection companyMetrics = null;

        when(companyService.getCompanyMetrics("1")).thenReturn(companyMetrics);

        mockMvc.perform(post("/api/v1/company-metrics/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(companyMetrics)))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void shouldEditCompanyMetricsReturn200() throws Exception {
        CompanyMetrics companyMetrics = InitializeData.initializeCompanyMetrics();
        CompanyMetricsProjection metrics = null;

        when(companyService.getCompanyMetrics("1")).thenReturn(metrics);
        companyMetrics.setDurationMonth("Mar,2021");

        mockMvc.perform(put("/api/v1/company-metrics/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(companyMetrics)))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldEditCompanyMetricsReturn400() throws Exception {
        CompanyMetricsProjection companyMetrics = null;

        when(companyService.getCompanyMetrics("1")).thenReturn(companyMetrics);

        mockMvc.perform(put("/api/v1/company-metrics/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(companyMetrics)))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void shouldDeleteCompanyMetricsReturn200() throws Exception {
        CompanyMetrics companyMetrics = InitializeData.initializeCompanyMetrics();
        CompanyMetricsProjection metrics = null;

        when(companyService.getCompanyMetrics("1")).thenReturn(null);
        companyService.saveCompanyMetrics(companyMetrics);

        mockMvc.perform(delete("/api/v1/company-metrics/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(companyMetrics)))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldDeleteCompanyMetricsReturn404() throws Exception {
        CompanyMetricsProjection companyMetrics = null;

        when(companyService.getCompanyMetrics("1")).thenReturn(companyMetrics);

        mockMvc.perform(delete("/api/v1/company-metrics/2")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(companyMetrics)))
                .andExpect(status().is4xxClientError());
    }
}

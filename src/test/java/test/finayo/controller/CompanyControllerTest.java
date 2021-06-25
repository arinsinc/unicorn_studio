package test.unicorn.studio.controller;

import com.unicorn.studio.controller.CompanyController;
import com.unicorn.studio.entity.Company;
import com.unicorn.studio.projection.CompanyPortfolioProjection;
import com.unicorn.studio.projection.CompanyProjection;
import com.unicorn.studio.service.CompanyService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import test.unicorn.studio.utils.InitializeData;
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
@Import(CompanyController.class)
@WebMvcTest(CompanyController.class)
@AutoConfigureMockMvc(addFilters = false)
@ContextConfiguration(classes = {SpringBootApplication.class})
public class CompanyControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CompanyService companyService;

    @Test
    public void shouldGetCompaniesReturns200() throws Exception {
        Company company = InitializeData.initializeCompany();
        List<CompanyPortfolioProjection> companyList = new ArrayList<>();
        Pageable pageable = PageRequest.of(0,20);

        when(companyService.getCompanies(pageable)).thenReturn(companyList);
        companyService.saveCompany(company);

        mockMvc.perform(get("/api/v1/companies")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldGetCompanyReturn200() throws Exception {
        Company company = InitializeData.initializeCompany();
        CompanyProjection result = null;

        when(companyService.getCompany("1")).thenReturn(result);
        companyService.saveCompany(company);

        mockMvc.perform(get("/api/v1/companies/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldGetCompanyReturn404() throws Exception {
        CompanyProjection company = null;

        when(companyService.getCompany("1")).thenReturn(company);

        mockMvc.perform(get("/api/v1/companies/11251")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void shouldSaveCompanyReturn201() throws Exception {
        CompanyProjection company = null;

        when(companyService.getCompany("1")).thenReturn(company);

        mockMvc.perform(post("/api/v1/companies/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(company)))
                .andExpect(status().isCreated());
    }

    @Test
    public void shouldSaveCompanyReturn400() throws Exception {
        CompanyProjection company = null;

        when(companyService.getCompany("1")).thenReturn(company);

        mockMvc.perform(post("/api/v1/companies/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(company)))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void shouldEditCompanyReturn200() throws Exception {
        Company company = InitializeData.initializeCompany();
        CompanyProjection result = null;

        when(companyService.getCompany("1")).thenReturn(result);
        company.setName("Google Inc.");

        mockMvc.perform(put("/api/v1/companies/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(company)))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldEditCompanyReturn400() throws Exception {
        CompanyProjection company = null;

        when(companyService.getCompany("1")).thenReturn(company);

        mockMvc.perform(put("/api/v1/companies/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(company)))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void shouldDeleteCompanyReturn200() throws Exception {
        Company company = InitializeData.initializeCompany();
        CompanyProjection result = null;

        when(companyService.getCompany("1")).thenReturn(result);
        companyService.saveCompany(company);

        mockMvc.perform(delete("/api/v1/companies/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(company)))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldDeleteCompanyReturn404() throws Exception {
        CompanyProjection company = null;

        when(companyService.getCompany("1")).thenReturn(company);

        mockMvc.perform(delete("/api/v1/companies/2")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(company)))
                .andExpect(status().is4xxClientError());
    }
}

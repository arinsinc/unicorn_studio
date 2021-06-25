package test.unicorn.studio.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.unicorn.studio.controller.FundingController;
import com.unicorn.studio.entity.Funding;
import com.unicorn.studio.projection.FundingProjection;
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
@Import(FundingController.class)
@WebMvcTest(FundingController.class)
@AutoConfigureMockMvc(addFilters = false)
@ContextConfiguration(classes = {SpringBootApplication.class})
public class FundingControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CompanyService companyService;

    @Test
    public void shouldGetCompaniesReturns200() throws Exception {
        Funding funding = InitializeData.initializeFunding();
        List<FundingProjection> fundingList = new ArrayList<>();

        when(companyService.getFundingList("1")).thenReturn(fundingList);
        companyService.saveFunding(funding);

        mockMvc.perform(get("/api/v1/funding/company_id=1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldGetFundingReturn200() throws Exception {
        Funding funding = InitializeData.initializeFunding();
        FundingProjection result = null;

        when(companyService.getFunding("1")).thenReturn(result);
        companyService.saveFunding(funding);

        mockMvc.perform(get("/api/v1/funding/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldGetFundingReturn404() throws Exception {
        FundingProjection funding = null;

        when(companyService.getFunding("1")).thenReturn(funding);

        mockMvc.perform(get("/api/v1/funding/11251")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void shouldSaveFundingReturn201() throws Exception {
        FundingProjection funding = null;

        when(companyService.getFunding("1")).thenReturn(funding);

        mockMvc.perform(post("/api/v1/funding/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(funding)))
                .andExpect(status().isCreated());
    }

    @Test
    public void shouldSaveFundingReturn400() throws Exception {
        FundingProjection funding = null;

        when(companyService.getFunding("1")).thenReturn(funding);

        mockMvc.perform(post("/api/v1/funding/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(funding)))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void shouldEditFundingReturn200() throws Exception {
        Funding funding = InitializeData.initializeFunding();
        FundingProjection result = null;

        when(companyService.getFunding("1")).thenReturn(result);
        funding.setFundingType("Series B");

        mockMvc.perform(put("/api/v1/funding/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(funding)))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldEditFundingReturn400() throws Exception {
        FundingProjection funding = null;

        when(companyService.getFunding("1")).thenReturn(funding);

        mockMvc.perform(put("/api/v1/funding/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(funding)))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void shouldDeleteFundingReturn200() throws Exception {
        Funding funding = InitializeData.initializeFunding();
        FundingProjection result = null;

        when(companyService.getFunding("1")).thenReturn(result);
        companyService.saveFunding(funding);

        mockMvc.perform(delete("/api/v1/funding/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(funding)))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldDeleteFundingReturn404() throws Exception {
        FundingProjection funding = null;

        when(companyService.getFunding("1")).thenReturn(funding);

        mockMvc.perform(delete("/api/v1/funding/2")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(funding)))
                .andExpect(status().is4xxClientError());
    }
}

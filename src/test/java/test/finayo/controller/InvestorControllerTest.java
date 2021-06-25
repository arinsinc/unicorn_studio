package test.unicorn.studio.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.unicorn.studio.controller.InvestorController;
import com.unicorn.studio.entity.Investor;
import com.unicorn.studio.projection.InvestorPortfolioProjection;
import com.unicorn.studio.projection.InvestorProjection;
import com.unicorn.studio.service.CompanyService;
import com.unicorn.studio.service.InvestorService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
@Import(InvestorController.class)
@WebMvcTest(InvestorController.class)
@AutoConfigureMockMvc(addFilters = false)
@ContextConfiguration(classes = {SpringBootApplication.class})
public class InvestorControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private InvestorService investorService;

    @Test
    public void shouldGetInvestorsReturns200() throws Exception {
        Investor investor = InitializeData.initializeInvestor();
        List<InvestorPortfolioProjection> investorList = new ArrayList<>();
        Pageable pageable = PageRequest.of(0,20);

        when(investorService.getInvestors(pageable)).thenReturn(investorList);
        investorService.saveInvestor(investor);

        mockMvc.perform(get("/api/v1/all-investors/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldGetInvestorReturn200() throws Exception {
        Investor investor = InitializeData.initializeInvestor();
        InvestorProjection result = null;

        when(investorService.getInvestor("1")).thenReturn(result);
        investorService.saveInvestor(investor);

        mockMvc.perform(get("/api/v1/investors/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldGetInvestorReturn404() throws Exception {
        InvestorProjection investor = null;

        when(investorService.getInvestor("1")).thenReturn(investor);

        mockMvc.perform(get("/api/v1/investors/11251")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void shouldSaveInvestorReturn201() throws Exception {
        InvestorProjection investor = null;

        when(investorService.getInvestor("1")).thenReturn(investor);

        mockMvc.perform(post("/api/v1/investors/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(investor)))
                .andExpect(status().isCreated());
    }

    @Test
    public void shouldSaveInvestorReturn400() throws Exception {
        InvestorProjection investor = null;

        when(investorService.getInvestor("1")).thenReturn(investor);

        mockMvc.perform(post("/api/v1/investors/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(investor)))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void shouldEditInvestorReturn200() throws Exception {
        Investor investor = InitializeData.initializeInvestor();
        InvestorProjection result = null;

        when(investorService.getInvestor("1")).thenReturn(result);
        investor.setFullName("Elon Musk");

        mockMvc.perform(put("/api/v1/investors/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(investor)))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldEditInvestorReturn400() throws Exception {
        InvestorProjection investor = null;

        when(investorService.getInvestor("1")).thenReturn(investor);

        mockMvc.perform(put("/api/v1/investors/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(investor)))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void shouldDeleteInvestorReturn200() throws Exception {
        Investor investor = InitializeData.initializeInvestor();
        InvestorProjection result = null;

        when(investorService.getInvestor("1")).thenReturn(result);
        investorService.saveInvestor(investor);

        mockMvc.perform(delete("/api/v1/investors/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(investor)))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldDeleteInvestorReturn404() throws Exception {
        InvestorProjection investor = null;

        when(investorService.getInvestor("1")).thenReturn(investor);

        mockMvc.perform(delete("/api/v1/investors/2")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(investor)))
                .andExpect(status().is4xxClientError());
    }
}

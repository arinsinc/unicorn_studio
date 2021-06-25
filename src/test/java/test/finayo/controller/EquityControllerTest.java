package test.unicorn.studio.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.unicorn.studio.controller.EquityController;
import com.unicorn.studio.entity.Equity;
import com.unicorn.studio.projection.EquityProjection;
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
@Import(EquityController.class)
@WebMvcTest(EquityController.class)
@AutoConfigureMockMvc(addFilters = false)
@ContextConfiguration(classes = {SpringBootApplication.class})
public class EquityControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private InvestorService investorService;

    @Test
    public void shouldGetEquitiesReturns200() throws Exception {
        Equity equity = InitializeData.initializeEquity();
        List<EquityProjection> equityList = new ArrayList<>();

        when(investorService.getEquities("1")).thenReturn(equityList);
        investorService.saveEquity(equity);

        mockMvc.perform(get("/api/v1/equities/company_id=1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldGetEquityReturn200() throws Exception {
        Equity equity = InitializeData.initializeEquity();
        EquityProjection result = null;

        when(investorService.getEquity("1")).thenReturn(result);
        investorService.saveEquity(equity);

        mockMvc.perform(get("/api/v1/equities/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldGetEquityReturn404() throws Exception {
        EquityProjection equity = null;

        when(investorService.getEquity("1")).thenReturn(equity);

        mockMvc.perform(get("/api/v1/equities/11251")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void shouldSaveEquityReturn201() throws Exception {
        EquityProjection equity = null;

        when(investorService.getEquity("1")).thenReturn(equity);

        mockMvc.perform(post("/api/v1/equities/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(equity)))
                .andExpect(status().isCreated());
    }

    @Test
    public void shouldSaveEquityReturn400() throws Exception {
        EquityProjection equity = null;

        when(investorService.getEquity("1")).thenReturn(equity);

        mockMvc.perform(post("/api/v1/equities/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(equity)))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void shouldEditEquityReturn200() throws Exception {
        Equity equity = InitializeData.initializeEquity();
        EquityProjection result = null;

        when(investorService.getEquity("1")).thenReturn(result);
        equity.setAllottedIn("Mar,2021");

        mockMvc.perform(put("/api/v1/equities/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(equity)))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldEditEquityReturn400() throws Exception {
        EquityProjection equity = null;

        when(investorService.getEquity("1")).thenReturn(equity);

        mockMvc.perform(put("/api/v1/equities/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(equity)))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void shouldDeleteEquityReturn200() throws Exception {
        Equity equity = InitializeData.initializeEquity();
        EquityProjection result = null;

        when(investorService.getEquity("1")).thenReturn(result);
        investorService.saveEquity(equity);

        mockMvc.perform(delete("/api/v1/equities/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(equity)))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldDeleteEquityReturn404() throws Exception {
        EquityProjection equity = null;

        when(investorService.getEquity("1")).thenReturn(equity);

        mockMvc.perform(delete("/api/v1/equities/2")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(equity)))
                .andExpect(status().is4xxClientError());
    }
}

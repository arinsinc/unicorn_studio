package test.unicorn.studio.controller;

import com.unicorn.studio.controller.PlanController;
import com.unicorn.studio.entity.Plan;
import com.unicorn.studio.projection.PlanProjection;
import com.unicorn.studio.service.CompanyService;
import com.unicorn.studio.service.SubscriptionService;
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
@Import(PlanController.class)
@WebMvcTest(PlanController.class)
@AutoConfigureMockMvc(addFilters = false)
@ContextConfiguration(classes = {SpringBootApplication.class})
public class PlanControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private SubscriptionService subscriptionService;

    @Test
    public void shouldGetCitiesReturns200() throws Exception {
        Plan plan = InitializeData.initializePlan();
        List<PlanProjection> planList = new ArrayList<>();

        when(subscriptionService.getPlans()).thenReturn(planList);
        subscriptionService.savePlan(plan);

        mockMvc.perform(get("/api/v1/plans")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldGetPlanReturn200() throws Exception {
        Plan plan = InitializeData.initializePlan();
        PlanProjection result = null;

        when(subscriptionService.getPlan("1")).thenReturn(result);
        subscriptionService.savePlan(plan);

        mockMvc.perform(get("/api/v1/plans/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldGetPlanReturn404() throws Exception {
        PlanProjection plan = null;

        when(subscriptionService.getPlan("1")).thenReturn(plan);

        mockMvc.perform(get("/api/v1/plans/11251")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void shouldSavePlanReturn201() throws Exception {
        PlanProjection plan = null;

        when(subscriptionService.getPlan("1")).thenReturn(plan);

        mockMvc.perform(post("/api/v1/plans/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(plan)))
                .andExpect(status().isCreated());
    }

    @Test
    public void shouldSavePlanReturn400() throws Exception {
        PlanProjection plan = null;

        when(subscriptionService.getPlan("1")).thenReturn(plan);

        mockMvc.perform(post("/api/v1/plans/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(plan)))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void shouldEditPlanReturn200() throws Exception {
        Plan plan = InitializeData.initializePlan();
        PlanProjection result = null;

        when(subscriptionService.getPlan("1")).thenReturn(result);
        plan.setPlanName("Business Pro");

        mockMvc.perform(put("/api/v1/plans/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(plan)))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldEditPlanReturn400() throws Exception {
        PlanProjection plan = null;

        when(subscriptionService.getPlan("1")).thenReturn(plan);

        mockMvc.perform(put("/api/v1/plans/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(plan)))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void shouldDeletePlanReturn200() throws Exception {
        Plan plan = InitializeData.initializePlan();
        PlanProjection result = null;

        when(subscriptionService.getPlan("1")).thenReturn(result);
        subscriptionService.savePlan(plan);

        mockMvc.perform(delete("/api/v1/plans/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(plan)))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldDeletePlanReturn404() throws Exception {
        PlanProjection plan = null;

        when(subscriptionService.getPlan("1")).thenReturn(plan);

        mockMvc.perform(delete("/api/v1/plans/2")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(plan)))
                .andExpect(status().is4xxClientError());
    }
}

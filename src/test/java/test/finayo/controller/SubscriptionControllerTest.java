package test.unicorn.studio.controller;

import com.unicorn.studio.controller.SubscriptionController;
import com.unicorn.studio.entity.Subscription;
import com.unicorn.studio.projection.SubscriptionProjection;
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

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@ExtendWith(SpringExtension.class)
@Import(SubscriptionController.class)
@WebMvcTest(SubscriptionController.class)
@AutoConfigureMockMvc(addFilters = false)
@ContextConfiguration(classes = {SpringBootApplication.class})
public class SubscriptionControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private SubscriptionService subscriptionService;

    @Test
    public void shouldGetCitiesReturns200() throws Exception {
        Subscription subscription = InitializeData.initializeSubscription();
        List<SubscriptionProjection> subscriptionList = new ArrayList<>();

        when(subscriptionService.getSubscriptions()).thenReturn(subscriptionList);
        subscriptionService.saveSubscription(subscription);

        mockMvc.perform(get("/api/v1/subscriptions")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldGetSubscriptionReturn200() throws Exception {
        Subscription subscription = InitializeData.initializeSubscription();
        SubscriptionProjection result = null;

        when(subscriptionService.getSubscription("1")).thenReturn(result);
        subscriptionService.saveSubscription(subscription);

        mockMvc.perform(get("/api/v1/subscriptions/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldGetSubscriptionReturn404() throws Exception {
        SubscriptionProjection subscription = null;

        when(subscriptionService.getSubscription("1")).thenReturn(subscription);

        mockMvc.perform(get("/api/v1/subscriptions/11251")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void shouldSaveSubscriptionReturn201() throws Exception {
        SubscriptionProjection subscription = null;

        when(subscriptionService.getSubscription("1")).thenReturn(subscription);

        mockMvc.perform(post("/api/v1/subscriptions/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(subscription)))
                .andExpect(status().isCreated());
    }

    @Test
    public void shouldSaveSubscriptionReturn400() throws Exception {
        SubscriptionProjection subscription = null;

        when(subscriptionService.getSubscription("1")).thenReturn(subscription);

        mockMvc.perform(post("/api/v1/subscriptions/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(subscription)))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void shouldEditSubscriptionReturn200() throws Exception {
        Subscription subscription = InitializeData.initializeSubscription();
        SubscriptionProjection result = null;

        when(subscriptionService.getSubscription("1")).thenReturn(result);
        subscription.setEndDate(LocalDate.now().minusMonths(2));

        mockMvc.perform(put("/api/v1/subscriptions/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(subscription)))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldEditSubscriptionReturn400() throws Exception {
        SubscriptionProjection subscription = null;

        when(subscriptionService.getSubscription("1")).thenReturn(subscription);

        mockMvc.perform(put("/api/v1/subscriptions/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(subscription)))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void shouldDeleteSubscriptionReturn200() throws Exception {
        Subscription subscription = InitializeData.initializeSubscription();
        SubscriptionProjection result = null;

        when(subscriptionService.getSubscription("1")).thenReturn(result);
        subscriptionService.saveSubscription(subscription);

        mockMvc.perform(delete("/api/v1/subscriptions/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(subscription)))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldDeleteSubscriptionReturn404() throws Exception {
        SubscriptionProjection subscription = null;

        when(subscriptionService.getSubscription("1")).thenReturn(subscription);

        mockMvc.perform(delete("/api/v1/subscriptions/2")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(subscription)))
                .andExpect(status().is4xxClientError());
    }
}

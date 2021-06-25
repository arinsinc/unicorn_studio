package test.unicorn.studio.service;

import com.unicorn.studio.entity.Subscription;
import com.unicorn.studio.dao.SubscriptionRepository;
import com.unicorn.studio.exception.NotFoundException;
import com.unicorn.studio.service.CompanyServiceImp;
import com.unicorn.studio.service.SubscriptionServiceImp;
import test.unicorn.studio.utils.InitializeData;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

@ExtendWith(SpringExtension.class)
public class SubscriptionServiceTest {
    @Mock
    private SubscriptionRepository subscriptionRepository;

    @InjectMocks
    private SubscriptionServiceImp subscriptionService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldGetAllSubscriptions() {
        // Given
        List<Subscription> subscriptionList = new ArrayList<>();
        Subscription subscription = new Subscription();

        // When
        when(subscriptionRepository.findAll()).thenReturn(subscriptionList);

        // Then
        assertEquals(subscriptionList, subscriptionService.getSubscriptions());
        verify(subscriptionRepository, times(1)).findAll();
    }

    @Test
    public void shouldSaveSubscription() {
        // Given
        Subscription subscription = InitializeData.initializeSubscription();

        // When
        when(subscriptionRepository.findById(1L)).thenReturn(Optional.of(subscription));

        // Then
        subscriptionService.saveSubscription(subscription);
        assertEquals(subscription, subscriptionService.getSubscription("1"));
        verify(subscriptionRepository, times(1)).save(subscription);
    }

    @Test
    public void shouldSaveSubscriptionThrowsException() {
        // Given
        Subscription subscription = new Subscription();

        // When
        when(subscriptionRepository.save(subscription)).thenThrow(new NullPointerException(""));

        // Then
        assertThrows(NullPointerException.class, () -> subscriptionService.saveSubscription(subscription));
        verify(subscriptionRepository, times(1)).save(subscription);
    }

    @Test
    public void shouldGetSubscription() {
        // Given
        Subscription subscription = InitializeData.initializeSubscription();

        // When
        when(subscriptionRepository.findById(1L)).thenReturn(Optional.of(subscription));

        // Then
        subscriptionService.saveSubscription(subscription);
        assertEquals(subscription, subscriptionService.getSubscription("1"));
        verify(subscriptionRepository, times(1)).findById(1L);
    }

    @Test
    public void shouldGetSubscriptionThrowsException() {
        // Given
        Subscription subscription = InitializeData.initializeSubscription();

        // When
        when(subscriptionRepository.findById(1L)).thenThrow(new NotFoundException("Did not find subscription id: 1"));

        // Then
        Exception exception = assertThrows(NotFoundException.class, () -> {subscriptionService.getSubscription("1");});
        assertEquals("Did not find subscription id: 1", exception.getMessage());
        verify(subscriptionRepository, times(1)).findById(1L);
    }

    @Test
    public void shouldEditSubscription() {
        // Given
        Subscription subscription = InitializeData.initializeSubscription();

        // When
        when(subscriptionRepository.findById(1L)).thenReturn(Optional.of(subscription));

        // Then
        subscriptionService.saveSubscription(subscription);
        subscription.setEndDate(LocalDate.now().plusDays(25));
        subscriptionService.saveSubscription(subscription);
        assertEquals(subscription, subscriptionService.getSubscription("1"));
        verify(subscriptionRepository, times(2)).save(subscription);
    }

    @Test
    public void shouldEditSubscriptionThrowsException() {
        // Given
        Subscription subscription = InitializeData.initializeSubscription();

        // When
        subscriptionService.saveSubscription(subscription);
        when(subscriptionRepository.save(subscription)).thenThrow(new NullPointerException(""));

        // Then
        subscription.setEndDate(null);
        assertThrows(NullPointerException.class, ()->{subscriptionService.saveSubscription(subscription);});
        verify(subscriptionRepository, times(2)).save(subscription);
    }

    @Test
    public void shouldDeleteSubscription() {
        // Given
        Subscription subscription = InitializeData.initializeSubscription();

        // When
        when(subscriptionRepository.findById(1L)).thenThrow(new NullPointerException());

        // Then
        subscriptionRepository.deleteById(1L);
        assertThrows(NullPointerException.class, ()->{subscriptionService.getSubscription("1");});
        verify(subscriptionRepository, times(1)).deleteById(1L);
    }

    @Test
    public void shouldDeleteSubscriptionThrowsException() {
        // Given
        Subscription subscription = InitializeData.initializeSubscription();

        // When
        when(subscriptionRepository.findById(1L)).thenThrow(new NotFoundException("Did not find subscription id: 1"));

        // Then
        Exception exception = assertThrows(NotFoundException.class, ()->{subscriptionService.deleteSubscription("1");});
        assertEquals("Did not find subscription id: 1", exception.getMessage());
    }
}

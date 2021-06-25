package test.unicorn.studio.service;

import com.unicorn.studio.entity.Notification;
import com.unicorn.studio.dao.NotificationRepository;
import com.unicorn.studio.exception.NotFoundException;
import com.unicorn.studio.service.UserServiceImp;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

@ExtendWith(SpringExtension.class)
public class NotificationServiceTest {
    @Mock
    private NotificationRepository notificationRepository;

    @InjectMocks
    private UserServiceImp userService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldSaveNotification() {
        // Given
        Notification notification = new Notification("New Subscription","EMPLOYER",false);

        // When
        when(notificationRepository.findById(1L)).thenReturn(Optional.of(notification));

        // Then
        userService.saveNotification(notification);
        assertEquals(notification, userService.getNotification(1L));
        verify(notificationRepository, times(1)).save(notification);
    }

    @Test
    public void shouldSaveNotificationThrowsException() {
        // Given
        Notification notification = new Notification();

        // When
        when(notificationRepository.save(notification)).thenThrow(new NullPointerException(""));

        // Then
        assertThrows(NullPointerException.class, () -> {userService.saveNotification(notification);});
        verify(notificationRepository, times(1)).save(notification);
    }

    @Test
    public void shouldGetNotification() {
        // Given
        Notification notification = new Notification("New Subscription","EMPLOYER",false);

        // When
        when(notificationRepository.findById(1L)).thenReturn(Optional.of(notification));

        // Then
        userService.saveNotification(notification);
        assertEquals(notification, userService.getNotification(1L));
        verify(notificationRepository, times(1)).findById(1L);
    }

    @Test
    public void shouldGetNotificationThrowsException() {
        // Given
        Notification notification = new Notification("New Subscription","EMPLOYER",false);

        // When
        when(notificationRepository.findById(1L)).thenThrow(new NotFoundException("Did not find notification id: 1"));

        // Then
        Exception exception = assertThrows(NotFoundException.class, () -> {userService.getNotification(1L);});
        assertEquals("Did not find notification id: 1", exception.getMessage());
        verify(notificationRepository, times(1)).findById(1L);
    }

    @Test
    public void shouldEditNotification() {
        // Given
        Notification notification = new Notification("New Subscription","EMPLOYER",false);

        // When
        when(notificationRepository.findById(1L)).thenReturn(Optional.of(notification));

        // Then
        userService.saveNotification(notification);
        notification.setNote("New Job");
        userService.saveNotification(notification);
        assertEquals(notification, userService.getNotification(1L));
        verify(notificationRepository, times(2)).save(notification);
    }

    @Test
    public void shouldEditNotificationThrowsException() {
        // Given
        Notification notification = new Notification("New Subscription","EMPLOYER",false);

        // When
        userService.saveNotification(notification);
        when(notificationRepository.save(notification)).thenThrow(new NullPointerException(""));

        // Then
        notification.setNote(null);
        assertThrows(NullPointerException.class, ()->{userService.saveNotification(notification);});
        verify(notificationRepository, times(2)).save(notification);
    }

    @Test
    public void shouldDeleteNotification() {
        // Given
        Notification notification = new Notification("New Subscription","EMPLOYER",false);

        // When
        when(notificationRepository.findById(1L)).thenThrow(new NullPointerException());

        // Then
        notificationRepository.deleteById(1L);
        assertThrows(NullPointerException.class, ()->{userService.getNotification(1L);});
        verify(notificationRepository, times(1)).deleteById(1L);
    }

    @Test
    public void shouldDeleteNotificationThrowsException() {
        // Given
        Notification notification = new Notification("New Subscription","EMPLOYER",false);

        // When
        when(notificationRepository.findById(1L)).thenThrow(new NotFoundException("Did not find notification id: 1"));

        // Then
        Exception exception = assertThrows(NotFoundException.class, ()->{userService.deleteNotification(1L);});
        assertEquals("Did not find notification id: 1", exception.getMessage());
    }
}

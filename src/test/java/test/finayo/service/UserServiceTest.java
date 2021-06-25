package test.unicorn.studio.service;

import com.unicorn.studio.entity.User;
import com.unicorn.studio.dao.UserRepository;
import com.unicorn.studio.exception.NotFoundException;
import com.unicorn.studio.projection.UserProjection;
import com.unicorn.studio.service.MailerService;
import com.unicorn.studio.service.UserServiceImp;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import test.unicorn.studio.utils.InitializeData;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

@ExtendWith(SpringExtension.class)
public class UserServiceTest {
    @Mock
    private UserRepository userRepository;

    @Mock
    private BCryptPasswordEncoder passwordEncoder;

    @Mock
    private MailerService mailerService;

    @InjectMocks
    private UserServiceImp userService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldGetAllUsers() {
        // Given
        List<User> userList = new ArrayList<>();
        User user = new User();

        // When
        when(userRepository.findAll()).thenReturn(userList);

        // Then
        assertEquals(userList, userService.getUsers());
        verify(userRepository, times(1)).findAll();
    }

    @Test
    public void shouldSaveUser() {
        // Given
        User user = InitializeData.initializeUser();

        // When
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        // Then
        userService.saveUser(user);
        assertEquals(user, userService.getUser("1"));
        verify(userRepository, times(1)).save(user);
    }

    @Test
    public void shouldSaveUserThrowsException() {
        // Given
        User user = new User();

        // When
        when(userRepository.save(user)).thenThrow(new NullPointerException(""));

        // Then
        assertThrows(NullPointerException.class, () -> userService.saveUser(user));
        verify(userRepository, times(1)).save(user);
    }

    @Test
    public void shouldGetUser() {
        // Given
        User user = InitializeData.initializeUser();

        // When
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        // Then
        userService.saveUser(user);
        assertEquals(user, userService.getUser("1"));
        verify(userRepository, times(1)).findById(1L);
    }

    @Test
    public void shouldGetUserThrowsException() {
        // Given
        User user = InitializeData.initializeUser();

        // When
        when(userRepository.findById(1L)).thenThrow(new NotFoundException("Did not find user id: 1"));

        // Then
        Exception exception = assertThrows(NotFoundException.class, () -> {userService.getUser("1");});
        assertEquals("Did not find user id: 1", exception.getMessage());
        verify(userRepository, times(1)).findById(1L);
    }

    @Test
    public void shouldEditUser() {
        // Given
        User user = InitializeData.initializeUser();

        // When
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        // Then
        userService.saveUser(user);
        user.setEmail("elon@tesla.com");
        userService.saveUser(user);
        assertEquals(user, userService.getUser("1"));
        verify(userRepository, times(2)).save(user);
    }

    @Test
    public void shouldEditUserThrowsException() {
        // Given
        User user = InitializeData.initializeUser();

        // When
        userService.saveUser(user);
        when(userRepository.save(user)).thenThrow(new NullPointerException(""));

        // Then
        user.setEmail(null);
        assertThrows(NullPointerException.class, ()->{userService.saveUser(user);});
        verify(userRepository, times(2)).save(user);
    }

    @Test
    public void shouldDeleteUser() {
        // Given
        User user = InitializeData.initializeUser();

        // When
        when(userRepository.findById(1L)).thenThrow(new NullPointerException());

        // Then
        userRepository.deleteById(1L);
        assertThrows(NullPointerException.class, ()->{userService.getUser("1");});
        verify(userRepository, times(1)).deleteById(1L);
    }

    @Test
    public void shouldDeleteUserThrowsException() {
        // Given
        User user = InitializeData.initializeUser();

        // When
        when(userRepository.findById(1L)).thenThrow(new NotFoundException("Did not find user id: 1"));

        // Then
        Exception exception = assertThrows(NotFoundException.class, ()->{userService.deleteUser("1");});
        assertEquals("Did not find user id: 1", exception.getMessage());
    }

    @Test
    public void shouldUserEmailConfirmed() {
        // Given
        User user = InitializeData.initializeUser();

        // When
        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));

        // Then
        userService.saveUser(user);
        userService.emailConfirmation(user.getEmail(),user.getConfirmationToken());
        assertEquals(null,user.getConfirmationToken());
        assertEquals(null,user.getConfirmationSendAt());
        verify(userRepository,times(1)).findByEmail(user.getEmail());
    }

    @Test
    public void shouldEmailConfirmedThrowsException() {
        // Given
        User user = InitializeData.initializeUser();

        // When
        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));

        // Then
        userService.saveUser(user);
        assertThrows(NotFoundException.class,()->{userService.emailConfirmation(user.getEmail(),null);});
        verify(userRepository,times(1)).findByEmail(user.getEmail());
    }

    @Test
    public void shouldResetPassword() {
        // Given
        User user = InitializeData.initializeUser();

        // When
        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));

        // Then
        userService.saveUser(user);
        userService.resetPasswordTokenGeneration(user.getEmail());
        assertNotEquals(null,user.getPasswordResetToken());
        assertNotEquals(null,user.getPasswordResetSendAt());
        verify(userRepository,times(2)).findByEmail(user.getEmail());
    }

    @Test
    public void shouldResetPasswordThrowsException() {
        // Given
        User user = InitializeData.initializeUser();

        // When
        when(userRepository.findByEmail(user.getEmail())).thenThrow(new NotFoundException(""));

        // Then
        assertThrows(NotFoundException.class,()->{userService.resetPasswordTokenGeneration(user.getEmail());});
        verify(userRepository,times(1)).findByEmail(user.getEmail());
    }

    @Test
    public void shouldResetPasswordConfirmed() {
        // Given
        User user = InitializeData.initializeUser();

        // When
        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));

        // Then
        userService.saveUser(user);
        userService.resetPassword(user.getEmail(),user.getPasswordResetToken(), "asdf1234");
        assertEquals(null,user.getPasswordResetToken());
        assertEquals(null,user.getPasswordResetSendAt());
        verify(userRepository,times(1)).findByEmail(user.getEmail());
    }

    @Test
    public void shouldPasswordResetConfirmationThrowsException() {
        // Given
        User user = InitializeData.initializeUser();

        // When
        when(userRepository.findByEmail(user.getEmail())).thenThrow(new NotFoundException(""));

        // Then
        userService.saveUser(user);
        assertThrows(NotFoundException.class,()->{userService.resetPassword(user.getEmail(),null,null);});
        verify(userRepository,times(1)).findByEmail(user.getEmail());
    }
}

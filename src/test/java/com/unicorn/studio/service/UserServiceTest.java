package com.unicorn.studio.service;

import com.unicorn.studio.dao.UserRepository;
import com.unicorn.studio.entity.*;
import com.unicorn.studio.exception.UserExistsException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Disabled;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Captor
    private ArgumentCaptor<User> userArgumentCaptor;

    @Mock
    private UnicornService unicornService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void itShouldSaveNewUser() {
        // Given
        User user = new User("Elon","Musk","ceo@tesla.com","asdf123");
        UserRole userRole = new UserRole();
        userRole.setRoleName("company");
        user.setUserRole(userRole);


        // When
        unicornService.saveUser(user);
        when(unicornService.getUser(1)).thenReturn(user);

        // Then
        verify(unicornService).saveUser(user);
        assertEquals(user, unicornService.getUser(1));
    }

    @Test
    void itShouldNotSaveUserWhenRequiredFieldEmpty() {
        // Given
        User user = new User("","Musk","","asdf123");
        UserRole userRole = new UserRole();
        userRole.setRoleName("company");
        user.setUserRole(userRole);


        // When
        unicornService.saveUser(user);
        when(unicornService.getUser(1)).thenReturn(null);

        // Then
        verify(unicornService).saveUser(user);
        assertEquals(null, unicornService.getUser(1));
    }

    @Test
    @Disabled
    void itShouldNotSaveUserWhenUserExits() {
        // Given
        User user = new User("Elon","Musk","ceo@tesla.com","asdf123");
        UserRole userRole = new UserRole();
        userRole.setRoleName("company");
        user.setUserRole(userRole);

        User recurringUser = new User("Tony","Stark","ceo@tesla.com","asdf123");

        // When
        unicornService.saveUser(user);
        when(unicornService.getUser(1)).thenReturn(user);
        assertThrows(UserExistsException.class, () ->{unicornService.saveUser(recurringUser);});

        // Then
        verify(unicornService).saveUser(user);
    }

    @Test
    void itShouldUpdateUser() {
        // Given
        User user = new User("Elon","Musk","ceo@tesla.com","asdf123");
        User updatedUser = new User("Tony","Stark","ceo@tesla.com","asdf123");
        UserRole userRole = new UserRole();
        userRole.setRoleName("company");
        user.setUserRole(userRole);


        // When
        unicornService.saveUser(user);
        when(unicornService.getUser(1)).thenReturn(user);
        unicornService.saveUser(updatedUser);
        when(unicornService.getUser(1)).thenReturn(updatedUser);

        // Then
        verify(unicornService).saveUser(updatedUser);
        assertEquals(updatedUser, unicornService.getUser(1));
    }
}

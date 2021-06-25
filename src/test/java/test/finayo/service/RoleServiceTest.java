package test.unicorn.studio.service;

import com.unicorn.studio.entity.Role;
import com.unicorn.studio.dao.RoleRepository;
import com.unicorn.studio.exception.NotFoundException;
import com.unicorn.studio.service.UserServiceImp;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

@ExtendWith(SpringExtension.class)
public class RoleServiceTest {
    @Mock
    private RoleRepository roleRepository;

    @InjectMocks
    private UserServiceImp userService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldGetAllRoles() {
        // Given
        List<Role> roleList = new ArrayList<>();
        Role role = new Role();

        // When
        when(roleRepository.findAll()).thenReturn(roleList);

        // Then
        assertEquals(roleList, userService.getRoles());
        verify(roleRepository, times(1)).findAll();
    }

    @Test
    public void shouldSaveRole() {
        // Given
        Role role = new Role("EMPLOYER");

        // When
        when(roleRepository.findById(1L)).thenReturn(Optional.of(role));

        // Then
        userService.saveRole(role);
        assertEquals(role, userService.getRole(1L));
        verify(roleRepository, times(1)).save(role);
    }

    @Test
    public void shouldSaveRoleThrowsException() {
        // Given
        Role role = new Role();

        // When
        when(roleRepository.save(role)).thenThrow(new NullPointerException(""));

        // Then
        assertThrows(NullPointerException.class, () -> userService.saveRole(role));
        verify(roleRepository, times(1)).save(role);
    }

    @Test
    public void shouldGetRole() {
        // Given
        Role role = new Role("EMPLOYER");

        // When
        when(roleRepository.findById(1L)).thenReturn(Optional.of(role));

        // Then
        userService.saveRole(role);
        assertEquals(role, userService.getRole(1L));
        verify(roleRepository, times(1)).findById(1L);
    }

    @Test
    public void shouldGetRoleThrowsException() {
        // Given
        Role role = new Role("EMPLOYER");

        // When
        when(roleRepository.findById(1L)).thenThrow(new NotFoundException("Did not find role id: 1"));

        // Then
        Exception exception = assertThrows(NotFoundException.class, () -> {userService.getRole(1L);});
        assertEquals("Did not find role id: 1", exception.getMessage());
        verify(roleRepository, times(1)).findById(1L);
    }

    @Test
    public void shouldEditRole() {
        // Given
        Role role = new Role("EMPLOYER");

        // When
        when(roleRepository.findById(1L)).thenReturn(Optional.of(role));

        // Then
        userService.saveRole(role);
        role.setName("New York");
        userService.saveRole(role);
        assertEquals(role, userService.getRole(1L));
        verify(roleRepository, times(2)).save(role);
    }

    @Test
    public void shouldEditRoleThrowsException() {
        // Given
        Role role = new Role("EMPLOYER");

        // When
        userService.saveRole(role);
        when(roleRepository.save(role)).thenThrow(new NullPointerException(""));

        // Then
        role.setName(null);
        assertThrows(NullPointerException.class, ()->{userService.saveRole(role);});
        verify(roleRepository, times(2)).save(role);
    }

    @Test
    public void shouldDeleteRole() {
        // Given
        Role role = new Role("EMPLOYER");

        // When
        when(roleRepository.findById(1L)).thenThrow(new NullPointerException());

        // Then
        roleRepository.deleteById(1L);
        assertThrows(NullPointerException.class, ()->{userService.getRole(1L);});
        verify(roleRepository, times(1)).deleteById(1L);
    }

    @Test
    public void shouldDeleteRoleThrowsException() {
        // Given
        Role role = new Role("EMPLOYER");

        // When
        when(roleRepository.findById(1L)).thenThrow(new NotFoundException("Did not find role id: 1"));

        // Then
        Exception exception = assertThrows(NotFoundException.class, ()->{userService.deleteRole(1L);});
        assertEquals("Did not find role id: 1", exception.getMessage());
    }
}

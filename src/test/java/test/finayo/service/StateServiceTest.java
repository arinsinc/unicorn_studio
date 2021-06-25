package test.unicorn.studio.service;

import com.unicorn.studio.entity.State;
import com.unicorn.studio.entity.State;
import com.unicorn.studio.dao.StateRepository;
import com.unicorn.studio.exception.NotFoundException;
import com.unicorn.studio.service.LocationServiceImp;
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
public class StateServiceTest {
    @Mock
    private StateRepository stateRepository;

    @InjectMocks
    private LocationServiceImp location;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldGetAllStates() {
        // Given
        List<State> stateList = new ArrayList<>();
        State state = new State("California");

        // When
        when(stateRepository.findAll()).thenReturn(stateList);

        // Then
        assertEquals(stateList, location.getStates());
        verify(stateRepository, times(1)).findAll();
    }

    @Test
    public void shouldSaveState() {
        // Given
        State state = new State("California");

        // When
        when(stateRepository.findById(1L)).thenReturn(Optional.of(state));

        // Then
        location.saveState(state);
        assertEquals(state, location.getState(1L));
        verify(stateRepository, times(1)).save(state);
    }

    @Test
    public void shouldSaveStateThrowsException() {
        // Given
        State state = new State();

        // When
        when(stateRepository.save(state)).thenThrow(new NullPointerException(""));

        // Then
        assertThrows(NullPointerException.class, () -> location.saveState(state));
        verify(stateRepository, times(1)).save(state);
    }

    @Test
    public void shouldGetState() {
        // Given
        State state = new State("California");

        // When
        when(stateRepository.findById(1L)).thenReturn(Optional.of(state));

        // Then
        location.saveState(state);
        assertEquals(state, location.getState(1L));
        verify(stateRepository, times(1)).findById(1L);
    }

    @Test
    public void shouldGetStateThrowsException() {
        // Given
        State state = new State("California");

        // When
        when(stateRepository.findById(1L)).thenThrow(new NotFoundException("Did not find state id: 1"));

        // Then
        Exception exception = assertThrows(NotFoundException.class, () -> {location.getState(1L);});
        assertEquals("Did not find state id: 1", exception.getMessage());
        verify(stateRepository, times(1)).findById(1L);
    }

    @Test
    public void shouldEditState() {
        // Given
        State state = new State("California");

        // When
        when(stateRepository.findById(1L)).thenReturn(Optional.of(state));

        // Then
        location.saveState(state);
        state.setName("New York");
        location.saveState(state);
        assertEquals(state, location.getState(1L));
        verify(stateRepository, times(2)).save(state);
    }

    @Test
    public void shouldEditStateThrowsException() {
        // Given
        State state = new State("California");

        // When
        location.saveState(state);
        when(stateRepository.save(state)).thenThrow(new NullPointerException(""));

        // Then
        state.setName(null);
        assertThrows(NullPointerException.class, ()->{location.saveState(state);});
        verify(stateRepository, times(2)).save(state);
    }

    @Test
    public void shouldDeleteState() {
        // Given
        State state = new State("California");

        // When
        when(stateRepository.findById(1L)).thenThrow(new NullPointerException());

        // Then
        stateRepository.deleteById(1L);
        assertThrows(NullPointerException.class, ()->{location.getState(1L);});
        verify(stateRepository, times(1)).deleteById(1L);
    }

    @Test
    public void shouldDeleteStateThrowsException() {
        // Given
        State state = new State("California");

        // When
        when(stateRepository.findById(1L)).thenThrow(new NotFoundException("Did not find state id: 1"));

        // Then
        Exception exception = assertThrows(NotFoundException.class, ()->{location.deleteState(1L);});
        assertEquals("Did not find state id: 1", exception.getMessage());
    }
}

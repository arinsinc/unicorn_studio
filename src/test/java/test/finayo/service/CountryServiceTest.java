package test.unicorn.studio.service;

import com.unicorn.studio.entity.Country;
import com.unicorn.studio.dao.CountryRepository;
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
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
public class CountryServiceTest {
    @Mock
    private CountryRepository countryRepository;

    @InjectMocks
    private LocationServiceImp location;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldGetAllCountries() {
        // Given
        List<Country> countryList = new ArrayList<>();
        Country country = new Country("United States","US","NA");

        // When
        when(countryRepository.findAll()).thenReturn(countryList);

        // Then
        assertEquals(countryList, location.getCountries());
        verify(countryRepository, times(1)).findAll();
    }

    @Test
    public void shouldSaveCountry() {
        // Given
        Country country = new Country("United States","US","NA");

        // When
        when(countryRepository.findById(1L)).thenReturn(Optional.of(country));

        // Then
        location.saveCountry(country);
        assertEquals(country, location.getCountry(1L));
        verify(countryRepository, times(1)).save(country);
    }

    @Test
    public void shouldSaveCountryThrowsException() {
        // Given
        Country country = new Country();

        // When
        when(countryRepository.save(country)).thenThrow(new NullPointerException(""));

        // Then
        assertThrows(NullPointerException.class, () -> {location.saveCountry(country);});
        verify(countryRepository, times(1)).save(country);
    }

    @Test
    public void shouldGetCountry() {
        // Given
        Country country = new Country("United States","US","NA");

        // When
        when(countryRepository.findById(1L)).thenReturn(Optional.of(country));

        // Then
        location.saveCountry(country);
        assertEquals(country, location.getCountry(1L));
        verify(countryRepository, times(1)).findById(1L);
    }

    @Test
    public void shouldGetCountryThrowsException() {
        // Given
        Country country = new Country("United States","US","NA");

        // When
        when(countryRepository.findById(1L)).thenThrow(new NotFoundException("Did not find country id: 1"));

        // Then
        Exception exception = assertThrows(NotFoundException.class, () -> {location.getCountry(1L);});
        assertEquals("Did not find country id: 1", exception.getMessage());
        verify(countryRepository, times(1)).findById(1L);
    }

    @Test
    public void shouldEditCountry() {
        // Given
        Country country = new Country("United States","US","NA");

        // When
        when(countryRepository.findById(1L)).thenReturn(Optional.of(country));

        // Then
        location.saveCountry(country);
        country.setName("New York");
        location.saveCountry(country);
        assertEquals(country, location.getCountry(1L));
        verify(countryRepository, times(2)).save(country);
    }

    @Test
    public void shouldEditCountryThrowsException() {
        // Given
        Country country = new Country("United States","US","NA");

        // When
        location.saveCountry(country);
        when(countryRepository.save(country)).thenThrow(new NullPointerException(""));

        // Then
        country.setName(null);
        assertThrows(NullPointerException.class, ()->{location.saveCountry(country);});
        verify(countryRepository, times(2)).save(country);
    }

    @Test
    public void shouldDeleteCountry() {
        // Given
        Country country = new Country("United States","US","NA");

        // When
        when(countryRepository.findById(1L)).thenThrow(new NullPointerException());

        // Then
        countryRepository.deleteById(1L);
        assertThrows(NullPointerException.class, ()->{location.getCountry(1L);});
        verify(countryRepository, times(1)).deleteById(1L);
    }

    @Test
    public void shouldDeleteCountryThrowsException() {
        // Given
        Country country = new Country("United States","US","NA");

        // When
        doNothing().doThrow(new NotFoundException("Did not find country id: 1")).when(countryRepository).deleteById(1L);

        // Then
        Exception exception = assertThrows(NotFoundException.class, ()->{location.deleteCountry(1L);});
        assertEquals("Did not find country id: 1", exception.getMessage());
    }
}

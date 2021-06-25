package test.unicorn.studio.service;

import com.unicorn.studio.entity.City;
import com.unicorn.studio.dao.CityRepository;
import com.unicorn.studio.exception.NotFoundException;
import com.unicorn.studio.service.LocationServiceImp;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import test.unicorn.studio.utils.InitializeData;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CityServiceTest {
    @Mock
    private CityRepository cityRepository;

    @InjectMocks
    private LocationServiceImp location;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void shouldGetAllCities() {
        // Given
        List<City> cityList = new ArrayList<>();
        City city = new City("San Francisco");

        // When
        when(cityRepository.findAll()).thenReturn(cityList);

        // Then
        assertEquals(cityList, location.getCities());
        verify(cityRepository, times(1)).findAll();
    }

    @Test
    public void shouldGetAllCitiesByState() {
        // Given
        List<City> cityList = new ArrayList<>();
        City city = new City("San Francisco");

        // When
        when(cityRepository.findByStateId(anyLong())).thenReturn(Optional.of(cityList));

        // Then
        assertEquals(cityList, location.getCitiesByState(anyLong()));
        verify(cityRepository, times(1)).findByStateId(anyLong());
    }

    @Test
    @Disabled
    public void shouldGetCityByName() {
        // Given
        List<City> cityList = new ArrayList<>();
        City city = new City("San Francisco");

        // When
        when(cityRepository.findByNameIgnoreCase(anyString())).thenReturn(Optional.of(cityList));

        // Then
        assertEquals(city, location.getCityByName("San Francisco"));
        verify(cityRepository, times(1)).findByNameIgnoreCase(anyString());
    }

    @Test
    public void shouldSaveCity() {
        // Given
        City city = new City("San Francisco");

        // When
        when(cityRepository.save(city)).thenReturn(city);
        location.saveCity(city);

        // Then
        ArgumentCaptor<City> cityArgumentCaptor = ArgumentCaptor.forClass(City.class);
        verify(cityRepository, times(1)).save(cityArgumentCaptor.capture());
        assertEquals(city, cityArgumentCaptor.getValue());
    }

    @Test
    public void shouldSaveCityThrowsException() {
        // Given
        City city = new City();

        // When
        when(cityRepository.save(city)).thenThrow(new NullPointerException(""));

        // Then
        assertThrows(NullPointerException.class, () -> {location.saveCity(city);});
        ArgumentCaptor<City> cityArgumentCaptor = ArgumentCaptor.forClass(City.class);
        verify(cityRepository, times(1)).save(cityArgumentCaptor.capture());
    }

    @Test
    public void shouldGetCity() {
        // Given
        City city = new City("San Francisco");

        // When
        when(cityRepository.findById(1L)).thenReturn(Optional.of(city));

        // Then
        assertEquals(city, location.getCity(1L));
        verify(cityRepository, times(1)).findById(1L);
    }

    @Test
    public void shouldGetCityThrowsException() {
        // Given
        City city = new City("San Francisco");

        // When
        when(cityRepository.findById(1L)).thenThrow(new NotFoundException("Did not find city id: 1"));

        // Then
        Exception exception = assertThrows(NotFoundException.class, () -> {location.getCity(1L);});
        assertEquals("Did not find city id: 1", exception.getMessage());
        verify(cityRepository, times(1)).findById(1L);
    }

    @Test
    public void shouldEditCity() {
        // Given
        City city = new City("San Francisco");

        // When
        when(cityRepository.save(city)).thenReturn(city);

        // Then
        city.setName("New York");
        location.saveCity(city);
        ArgumentCaptor<City> cityArgumentCaptor = ArgumentCaptor.forClass(City.class);
        verify(cityRepository, times(1)).save(cityArgumentCaptor.capture());
        assertEquals(city, cityArgumentCaptor.getValue());
    }

    @Test
    public void shouldEditCityThrowsException() {
        // Given
        City city = new City("San Francisco");

        // When
        when(cityRepository.save(city)).thenThrow(new NullPointerException(""));

        // Then
        city.setName(null);
        assertThrows(NullPointerException.class, ()->{location.saveCity(city);});
        verify(cityRepository, times(1)).save(city);
    }

    @Test
    public void shouldDeleteCity() {
        // Given
        City city = new City("San Francisco");

        // When
        doNothing().when(cityRepository).deleteById(1L);

        // Then
        location.deleteCity(1L);
        verify(cityRepository, times(1)).deleteById(1L);
    }

    @Test
    public void shouldDeleteCityThrowsException() {
        // Given
        City city = new City("San Francisco");

        // When
        doNothing().doThrow(new NotFoundException("Did not find city id: 1")).when(cityRepository).deleteById(1L);

        // Then
        location.deleteCity(1L);
        Exception exception = assertThrows(NotFoundException.class, ()->{location.deleteCity(1L);});
        assertEquals("Did not find city id: 1", exception.getMessage());
    }
}

package com.unicorn.studio.service;
import java.util.List;

import com.unicorn.studio.entity.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

public interface LocationService {
    List<City> getCities();
    List<City> getCitiesByState(long stateId);
    City saveCity(City city);
    City getCity(long id);
    City getCityByName(String name);
    void deleteCity(long id);

    List<Country> getCountries();
    Country saveCountry(Country country);
    Country getCountry(long id);
    void deleteCountry(long id);

    List<State> getStates();
    List<State> getStatesByCountry(long countryId);
    State saveState(State state);
    State getState(long id);
    void deleteState(long id);
}

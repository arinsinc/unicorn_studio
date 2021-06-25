package com.unicorn.studio.service;



import com.unicorn.studio.dao.CityRepository;
import com.unicorn.studio.dao.CountryRepository;
import com.unicorn.studio.dao.StateRepository;
import com.unicorn.studio.entity.City;
import com.unicorn.studio.entity.Country;
import com.unicorn.studio.entity.State;
import com.unicorn.studio.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class LocationServiceImp implements LocationService {
    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private CountryRepository countryRepository;

    @Autowired
    private StateRepository stateRepository;

    public LocationServiceImp(CityRepository cityRepository, CountryRepository countryRepository, StateRepository stateRepository) {
        this.cityRepository = cityRepository;
        this.countryRepository = countryRepository;
        this.stateRepository = stateRepository;
    }

    /**
     * Get all cities
     * @return List<City>
     */
    @Override
    @Transactional
    public List<City> getCities() {
        return cityRepository.findAll();
    }

    /**
     * Get cities by state id
     * @param stateId
     * @return List<City>
     */
    @Override
    @Transactional
    public List<City> getCitiesByState(long stateId) {
        return cityRepository.findByStateId(stateId).orElseThrow(()->new NotFoundException("State not found"));
    }

    /**
     * Save city
     * @param city
     */
    @Override
    @Transactional
    public City saveCity(City city) {
        return cityRepository.save(city);
    }

    /**
     * Get city by id
     * @param id
     * @return city
     */
    @Override
    @Transactional
    public City getCity(long id) {
        Optional<City> result = cityRepository.findById(id);
        City city = null;
        if (result.isPresent()) {
            city = result.get();
        }
        else {
            throw new NotFoundException("Did not find city id: " + id);
        }
        return city;
    }

    /**
     * Get city by name
     * @param name
     * @return city
     */
    @Override
    @Transactional
    public City getCityByName(String name) {
        List<City> city = cityRepository.findByNameIgnoreCase(name).get();
        if (city.isEmpty()) {
            throw new NotFoundException("Did not find city: " + name);
        }
        return city.get(0);
    }

    /**
     * Delete city by id
     * @param id
     */
    @Override
    @Transactional
    public void deleteCity(long id) {
        cityRepository.deleteById(id);
    }

    /**
     * Get all countries
     * @return List<Country>
     */
    @Override
    @Transactional
    public List<Country> getCountries() {
        return countryRepository.findAll();
    }

    /**
     * Save country
     * @param country
     */
    @Override
    @Transactional
    public Country saveCountry(Country country) {
        return countryRepository.save(country);
    }

    /**
     * Get country by id
     * @param id
     * @return country
     */
    @Override
    @Transactional
    public Country getCountry(long id) {
        Optional<Country> result = countryRepository.findById(id);
        Country country = null;
        if (result.isPresent()) {
            country = result.get();
        }
        else {
            throw new NotFoundException("Did not find country id: " + id);
        }
        return country;
    }

    /**
     * Delete country by id
     * @param id
     */
    @Override
    @Transactional
    public void deleteCountry(long id) {
        countryRepository.deleteById(id);
    }

    /**
     * Get all states
     * @return List<State>
     */
    @Override
    @Transactional
    public List<State> getStates() {
        return stateRepository.findAll();
    }

    /**
     * Get states by country id
     * @param countryId
     * @return List<State>
     */
    @Override
    @Transactional
    public List<State> getStatesByCountry(long countryId) {
        return stateRepository.findByCountryId(countryId).orElseThrow(()->new NotFoundException("Country not found"));
    }

    /**
     * Save state
     * @param state
     */
    @Override
    @Transactional
    public State saveState(State state) {
        return stateRepository.save(state);
    }

    /**
     * Get state by id
     * @param id
     * @return state
     */
    @Override
    @Transactional
    public State getState(long id) {
        Optional<State> result = stateRepository.findById(id);
        State state = null;
        if (result.isPresent()) {
            state = result.get();
        }
        else {
            throw new NotFoundException("Did not find state id: " + id);
        }
        return state;
    }

    /**
     * Delete state by id
     * @param id
     */
    @Override
    @Transactional
    public void deleteState(long id) {
        stateRepository.deleteById(id);
    }
}

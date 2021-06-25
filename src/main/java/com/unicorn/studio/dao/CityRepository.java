package com.unicorn.studio.dao;



import org.springframework.data.jpa.repository.JpaRepository;
import com.unicorn.studio.entity.City;

import java.util.List;
import java.util.Optional;

public interface CityRepository extends JpaRepository<City, Long> {
    Optional<List<City>> findByNameIgnoreCase(String name);

    Optional<List<City>> findByStateId(long stateId);
}

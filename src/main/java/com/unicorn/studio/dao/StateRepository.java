package com.unicorn.studio.dao;



import org.springframework.data.jpa.repository.JpaRepository;
import com.unicorn.studio.entity.State;

import java.util.List;
import java.util.Optional;

public interface StateRepository extends JpaRepository<State, Long> {
    Optional<List<State>> findByCountryId(long countryId);
}

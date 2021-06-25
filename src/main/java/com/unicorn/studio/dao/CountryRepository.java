package com.unicorn.studio.dao;



import org.springframework.data.jpa.repository.JpaRepository;
import com.unicorn.studio.entity.Country;

public interface CountryRepository extends JpaRepository<Country, Long> {

}

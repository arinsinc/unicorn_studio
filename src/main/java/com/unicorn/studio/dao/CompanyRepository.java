package com.unicorn.studio.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.unicorn.studio.entity.Company;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Integer> {

}

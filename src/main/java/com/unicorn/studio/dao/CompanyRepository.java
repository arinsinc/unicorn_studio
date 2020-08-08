package com.unicorn.studio.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import com.unicorn.studio.entity.Company;

public interface CompanyRepository extends JpaRepository<Company, Long> {

}

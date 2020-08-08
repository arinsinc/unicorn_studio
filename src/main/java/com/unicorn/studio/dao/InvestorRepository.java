package com.unicorn.studio.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import com.unicorn.studio.entity.Investor;

public interface InvestorRepository extends JpaRepository<Investor, Long> {

}

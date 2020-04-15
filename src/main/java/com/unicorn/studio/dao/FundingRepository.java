package com.unicorn.studio.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import com.unicorn.studio.entity.Funding;

public interface FundingRepository extends JpaRepository<Funding, Integer> {

}

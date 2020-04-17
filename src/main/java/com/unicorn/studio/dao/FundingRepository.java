package com.unicorn.studio.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.unicorn.studio.entity.Funding;

@Repository
public interface FundingRepository extends JpaRepository<Funding, Integer> {

}

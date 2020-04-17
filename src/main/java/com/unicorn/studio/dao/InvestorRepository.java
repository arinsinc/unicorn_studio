package com.unicorn.studio.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.unicorn.studio.entity.Investor;

@Repository
public interface InvestorRepository extends JpaRepository<Investor, Integer> {

}

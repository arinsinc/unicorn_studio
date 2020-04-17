package com.unicorn.studio.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.unicorn.studio.entity.Club;

@Repository
public interface ClubRepository extends JpaRepository<Club, Integer> {

}

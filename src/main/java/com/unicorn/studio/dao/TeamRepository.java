package com.unicorn.studio.dao;

import com.unicorn.studio.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamRepository extends JpaRepository<Team, Long> {
}

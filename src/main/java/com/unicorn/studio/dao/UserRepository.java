package com.unicorn.studio.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.unicorn.studio.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
}

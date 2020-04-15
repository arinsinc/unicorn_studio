package com.unicorn.studio.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.unicorn.studio.entity.User;

public interface UserRepository extends JpaRepository<User, Integer> {

}

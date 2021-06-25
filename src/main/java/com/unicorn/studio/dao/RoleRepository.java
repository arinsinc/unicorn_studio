package com.unicorn.studio.dao;



import org.springframework.data.jpa.repository.JpaRepository;
import com.unicorn.studio.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);
}

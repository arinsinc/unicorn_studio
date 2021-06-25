package com.unicorn.studio.dao;



import com.unicorn.studio.projection.UserProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import com.unicorn.studio.entity.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    @Query("SELECT u.uid, u.fullName, u.email FROM User u WHERE u.email = :email")
    Optional<UserProjection> findUserByEmail(@Param("email") String email);

    @Query("SELECT u.uid, u.fullName, u.email FROM User u WHERE u.uid = :uid")
    Optional<UserProjection> findUserByUid(String uid);

    void deleteByUid(String uid);

    Optional<User> findByEmail(String email);

    Optional<User> findByUid(String uid);

    Optional<User> findByProvider(String provider);
}

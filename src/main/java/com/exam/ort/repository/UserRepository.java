package com.exam.ort.repository;

import com.exam.ort.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("SELECT CASE WHEN COUNT(u) > 0 THEN TRUE ELSE FALSE END FROM User u WHERE u.email = ?1")
    boolean existsByEmail(String email);

    @Query("SELECT u FROM User u WHERE u.email = ?1")
    Optional<User> findByEmail(String email);

    @Query("SELECT u FROM User u WHERE u.role= 'USER'")
    List<User> findAllUsers();

    @Query("SELECT u FROM User u WHERE u.role = 'USER' AND u.accountNonLocked = FALSE")
    List<User> findLockedUsers();

    @Query("SELECT u FROM User u WHERE u.role = 'USER' AND u.accountNonLocked = TRUE")
    List<User> findUnlockedUsers();
}


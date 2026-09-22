package com.sagar.user_service.repository;


import com.sagar.user_service.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long>{

    Optional<User> findByName(String username);

    @Query("SELECT u.email FROM User u WHERE u.email IS NOT NULL")
    List<String> findAllUserEmails();
}

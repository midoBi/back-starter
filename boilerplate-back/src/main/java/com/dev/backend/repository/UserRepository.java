package com.dev.backend.repository;

import com.dev.backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByCinEqualsIgnoreCase(String cin);

}

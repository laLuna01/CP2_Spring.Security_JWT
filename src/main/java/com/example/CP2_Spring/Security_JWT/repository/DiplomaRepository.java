package com.example.CP2_Spring.Security_JWT.repository;

import com.example.CP2_Spring.Security_JWT.model.Diploma;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface DiplomaRepository extends JpaRepository<Diploma, String> {
}

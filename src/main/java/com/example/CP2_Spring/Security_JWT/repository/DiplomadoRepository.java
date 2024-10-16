package com.example.CP2_Spring.Security_JWT.repository;

import com.example.CP2_Spring.Security_JWT.model.Diplomado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DiplomadoRepository extends JpaRepository<Diplomado, Long> {
}

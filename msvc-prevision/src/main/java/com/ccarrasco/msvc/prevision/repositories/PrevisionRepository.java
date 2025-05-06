package com.ccarrasco.msvc.prevision.repositories;

import com.ccarrasco.msvc.prevision.models.Prevision;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PrevisionRepository extends JpaRepository<Prevision, Long> {
    Optional<Prevision> findByNombre(String nombre);
}

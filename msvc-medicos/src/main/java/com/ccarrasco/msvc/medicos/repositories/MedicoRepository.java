package com.ccarrasco.msvc.medicos.repositories;

import com.ccarrasco.msvc.medicos.models.entities.Medico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MedicoRepository extends JpaRepository<Medico, Long> {
    Optional<Medico> findByRunMedico(String RunMedico);

}

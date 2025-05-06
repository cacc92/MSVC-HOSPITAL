package com.ccarrasco.msvc.pacientes.repositories;

import com.ccarrasco.msvc.pacientes.models.entities.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PacienteRepository extends JpaRepository<Paciente, Long> {
}

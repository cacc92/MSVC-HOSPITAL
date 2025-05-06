package com.ccarrasco.msvc.pacientes.repositories;

import com.ccarrasco.msvc.pacientes.models.entities.FichaPaciente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FichaPacienteRepository extends JpaRepository<FichaPaciente, Long> {

}

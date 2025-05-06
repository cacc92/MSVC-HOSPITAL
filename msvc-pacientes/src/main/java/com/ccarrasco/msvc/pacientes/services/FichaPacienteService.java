package com.ccarrasco.msvc.pacientes.services;

import com.ccarrasco.msvc.pacientes.dtos.FichaPacienteCreationDTO;
import com.ccarrasco.msvc.pacientes.models.entities.FichaPaciente;

import java.util.List;

public interface FichaPacienteService {

    List<FichaPaciente> findAll();
    FichaPaciente findById(Long id);
    FichaPaciente save(FichaPacienteCreationDTO fichaPaciente);
}

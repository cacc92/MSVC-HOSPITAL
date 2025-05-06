package com.ccarrasco.msvc.pacientes.services;

import com.ccarrasco.msvc.pacientes.models.Paciente;

import java.util.List;

public interface PacienteService {

    List<Paciente> findAll();
    Paciente findById(Long id);
    Paciente save(Paciente paciente);
}

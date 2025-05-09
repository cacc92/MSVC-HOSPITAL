package com.ccarrasco.msvc.pacientes.services;

import com.ccarrasco.msvc.pacientes.dtos.AtencionPacienteDTO;
import com.ccarrasco.msvc.pacientes.dtos.PacienteTotalAtencionesDTO;
import com.ccarrasco.msvc.pacientes.models.entities.Paciente;

import java.util.List;

public interface PacienteService {

    List<Paciente> findAll();
    Paciente findById(Long id);
    Paciente save(Paciente paciente);
    List<AtencionPacienteDTO> findAtencionesByPacienteId(Long idPaciente);
    PacienteTotalAtencionesDTO findTotalAtencionesById(Long idPaciente);
}

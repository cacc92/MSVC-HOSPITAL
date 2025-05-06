package com.ccarrasco.msvc.pacientes.services;

import com.ccarrasco.msvc.pacientes.clients.PrevisionClientRest;
import com.ccarrasco.msvc.pacientes.exceptions.PacienteException;
import com.ccarrasco.msvc.pacientes.models.Prevision;
import com.ccarrasco.msvc.pacientes.models.entities.Paciente;
import com.ccarrasco.msvc.pacientes.repositories.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PacienteServiceImpl implements PacienteService {

    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private PrevisionClientRest previsionClientRest;

    @Override
    public List<Paciente> findAll() {
        return this.pacienteRepository.findAll();
    }

    @Override
    public Paciente findById(Long id) {
        return this.pacienteRepository.findById(id).orElseThrow(
                () -> new PacienteException("El paciente con id "+id+" no se encuentra en la base de datos")
        );
    }

    @Override
    public Paciente save(Paciente paciente) {
        Prevision prevision = this.previsionClientRest.findById(paciente.getIdPrevision());
        return this.pacienteRepository.save(paciente);
    }
}

package com.ccarrasco.msvc.pacientes.services;

import com.ccarrasco.msvc.pacientes.dtos.FichaPacienteCreationDTO;
import com.ccarrasco.msvc.pacientes.exceptions.FichaPacienteException;
import com.ccarrasco.msvc.pacientes.models.FichaPaciente;
import com.ccarrasco.msvc.pacientes.models.Paciente;
import com.ccarrasco.msvc.pacientes.repositories.FichaPacienteRepository;
import com.ccarrasco.msvc.pacientes.repositories.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class FichaPacienteServiceImpl implements FichaPacienteService {

    @Autowired
    private FichaPacienteRepository fichaPacienteRepository;

    @Autowired
    private PacienteService pacienteService;

    @Transactional(readOnly = true)
    @Override
    public List<FichaPaciente> findAll() {
        return this.fichaPacienteRepository.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public FichaPaciente findById(Long id) {
        return this.fichaPacienteRepository.findById(id).orElseThrow(
                () -> new FichaPacienteException("FichaPaciente con id "+id+" no encontrada")
        );
    }

    @Transactional
    @Override
    public FichaPaciente save(FichaPacienteCreationDTO fichaPaciente) {
        Paciente paciente = this.pacienteService.findById(fichaPaciente.getIdPaciente());
        FichaPaciente fichaPacienteEntity = new FichaPaciente();
        fichaPacienteEntity.setPaciente(paciente);
        fichaPacienteEntity.setDatosPersonales(fichaPaciente.getDatosPersonales());
        return this.fichaPacienteRepository.save(fichaPacienteEntity);
    }
}

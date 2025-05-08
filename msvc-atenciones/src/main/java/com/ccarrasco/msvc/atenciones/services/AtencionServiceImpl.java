package com.ccarrasco.msvc.atenciones.services;

import com.ccarrasco.msvc.atenciones.clients.MedicoClientRest;
import com.ccarrasco.msvc.atenciones.clients.PacienteClientRest;
import com.ccarrasco.msvc.atenciones.exceptions.AtencionException;
import com.ccarrasco.msvc.atenciones.models.Atencion;
import com.ccarrasco.msvc.atenciones.models.Medico;
import com.ccarrasco.msvc.atenciones.models.Paciente;
import com.ccarrasco.msvc.atenciones.repositories.AtencionRepository;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AtencionServiceImpl implements AtencionService {

    @Autowired
    private AtencionRepository atencionRepository;

    @Autowired
    private MedicoClientRest medicoClientRest;

    @Autowired
    private PacienteClientRest pacienteClientRest;

    @Override
    public List<Atencion> findAll() {
        return this.atencionRepository.findAll();
    }

    @Override
    public Atencion findById(Long id) {
        return this.atencionRepository.findById(id).orElseThrow(
                () -> new AtencionException("El atencion con id: " + id + " no se encuentra en la base de datos")
        );
    }

    @Override
    public Atencion save(Atencion atencion) {
        try {
            Medico medico = this.medicoClientRest.findById(atencion.getIdMedico());
            Paciente paciente = this.pacienteClientRest.findById(atencion.getIdPaciente());
        }catch (FeignException ex) {
            throw new AtencionException("Existen problemas con la asoción médico paciente");
        }
        return this.atencionRepository.save(atencion);
    }

    @Override
    public List<Atencion> findByIdPaciente(Long idPaciente) {
        return this.atencionRepository.findByIdPaciente(idPaciente);
    }

    @Override
    public List<Atencion> findByIdMedico(Long idMedico) {
        return this.atencionRepository.findByIdMedico(idMedico);
    }
}

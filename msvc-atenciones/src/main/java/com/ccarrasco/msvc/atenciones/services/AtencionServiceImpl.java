package com.ccarrasco.msvc.atenciones.services;

import com.ccarrasco.msvc.atenciones.clients.MedicoClientRest;
import com.ccarrasco.msvc.atenciones.clients.PacienteClientRest;
import com.ccarrasco.msvc.atenciones.clients.PrevisionClientRest;
import com.ccarrasco.msvc.atenciones.dtos.*;
import com.ccarrasco.msvc.atenciones.exceptions.AtencionException;
import com.ccarrasco.msvc.atenciones.models.entities.Atencion;
import com.ccarrasco.msvc.atenciones.models.Medico;
import com.ccarrasco.msvc.atenciones.models.Paciente;
import com.ccarrasco.msvc.atenciones.models.Prevision;
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

    @Autowired
    private PrevisionClientRest previsionClientRest;

    @Override
    public List<AtencionDTO> findAll() {
        return this.atencionRepository.findAll().stream().map(atencion -> {

            Medico medico = null;
            try {
                medico = this.medicoClientRest.findById(atencion.getIdMedico());
            }catch (FeignException ex) {
                throw new AtencionException("El medico buscado no existe");
            }

            Paciente paciente = null;
            Prevision prevision = null;
            try {
                paciente = this.pacienteClientRest.findById(atencion.getIdPaciente());
                prevision = this.previsionClientRest.findById(paciente.getIdPrevision());
            }catch (FeignException ex) {
                throw new AtencionException("El paciente no existe en la base de datos");
            }

            MedicoDTO medicoDTO = new MedicoDTO();
            medicoDTO.setRunMedico(medico.getRunMedico());
            medicoDTO.setNombreCompleto(medico.getNombreCompleto());
            medicoDTO.setEspecialidad(medico.getEspecialidad());

            PacienteDTO pacienteDTO = new PacienteDTO();
            pacienteDTO.setCorreo(paciente.getCorreo());
            pacienteDTO.setRun(paciente.getRun());
            pacienteDTO.setFechaNacimiento(paciente.getFechaNacimiento());
            pacienteDTO.setPrevision(prevision.getNombre());
            pacienteDTO.setNombreCompleto(paciente.getNombres()+" "+paciente.getApellidos());

            AtencionDTO atencionDTO = new AtencionDTO();
            atencionDTO.setMedico(medicoDTO);
            atencionDTO.setPaciente(pacienteDTO);
            return atencionDTO;

        }).toList();
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
    public List<Atencion> findByMedicoId(Long medicoId) {
        return this.atencionRepository.findByIdMedico(medicoId);
    }

    @Override
    public List<Atencion> findByPacienteId(Long pacienteId) {
        return this.atencionRepository.findByIdPaciente(pacienteId);
    }
}

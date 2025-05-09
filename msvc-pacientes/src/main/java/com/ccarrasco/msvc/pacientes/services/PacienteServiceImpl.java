package com.ccarrasco.msvc.pacientes.services;

import com.ccarrasco.msvc.pacientes.clients.AtencionClientRest;
import com.ccarrasco.msvc.pacientes.clients.MedicoClientRest;
import com.ccarrasco.msvc.pacientes.clients.PrevisionClientRest;
import com.ccarrasco.msvc.pacientes.dtos.AtencionPacienteDTO;
import com.ccarrasco.msvc.pacientes.dtos.MedicoDTO;
import com.ccarrasco.msvc.pacientes.exceptions.PacienteException;
import com.ccarrasco.msvc.pacientes.models.Atencion;
import com.ccarrasco.msvc.pacientes.models.Medico;
import com.ccarrasco.msvc.pacientes.models.Prevision;
import com.ccarrasco.msvc.pacientes.models.entities.Paciente;
import com.ccarrasco.msvc.pacientes.repositories.PacienteRepository;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PacienteServiceImpl implements PacienteService {

    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private PrevisionClientRest previsionClientRest;

    @Autowired
    private AtencionClientRest atencionClientRest;

    @Autowired
    private MedicoClientRest medicoClientRest;

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
        try {
            Prevision prevision = this.previsionClientRest.findById(paciente.getIdPrevision());
            return this.pacienteRepository.save(paciente);
        }catch (FeignException ex) {
            throw new PacienteException(ex.getMessage());
        }
    }

    @Override
    public List<AtencionPacienteDTO> findAtencionesByPacienteId(Long idPaciente) {
        // Se verifica que el paciente exista
        Paciente paciente = this.findById(idPaciente);

        // En caso que exista el paciente se realiza la obtención de todas sus atenciones
        List<Atencion> atenciones = this.atencionClientRest.findByIdPaciente(idPaciente);
        if (!atenciones.isEmpty()){
             return atenciones.stream().map(atencion -> {
                AtencionPacienteDTO dto = new AtencionPacienteDTO();
                 Medico medico = null;
                 try{
                     medico = this.medicoClientRest.findById(atencion.getIdMedico());
                 }catch (FeignException ex) {
                     throw new PacienteException("Al momento de generar la vista de atenciones de pacientes no " +
                             "se pudo encontrar al medico con id "+atencion.getIdMedico());
                 }

                 // Se genera el DTO de medico DTO.
                 MedicoDTO medicoDTO = new MedicoDTO();
                 medicoDTO.setRunMedico(medico.getRunMedico());
                 medicoDTO.setEspecialidad(medico.getEspecialidad());
                 medicoDTO.setNombreCompleto(medico.getNombreCompleto());


                 dto.setComentario(atencion.getComentario());
                 dto.setHoraAtencion(atencion.getHoraAtencion());
                 dto.setCosto(atencion.getCosto());
                 dto.setMedico(medicoDTO);

                 return dto;
            }).toList();
        }
        return List.of();
    }

}

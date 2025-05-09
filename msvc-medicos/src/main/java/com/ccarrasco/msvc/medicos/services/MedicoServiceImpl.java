package com.ccarrasco.msvc.medicos.services;

import com.ccarrasco.msvc.medicos.clients.AtencionClientRest;
import com.ccarrasco.msvc.medicos.clients.PacienteClientRest;
import com.ccarrasco.msvc.medicos.clients.PrevisionClientRest;
import com.ccarrasco.msvc.medicos.dtos.AtencionMedicoDTO;
import com.ccarrasco.msvc.medicos.dtos.PacienteDTO;
import com.ccarrasco.msvc.medicos.exceptions.MedicoException;
import com.ccarrasco.msvc.medicos.models.Atencion;
import com.ccarrasco.msvc.medicos.models.Paciente;
import com.ccarrasco.msvc.medicos.models.entities.Medico;
import com.ccarrasco.msvc.medicos.repositories.MedicoRepository;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MedicoServiceImpl implements MedicoService {

    @Autowired
    private MedicoRepository medicoRepository;

    @Autowired
    private AtencionClientRest atencionClientRest;

    @Autowired
    private PacienteClientRest pacienteClientRest;

    @Autowired
    private PrevisionClientRest previsionClientRest;

    @Override
    public List<Medico> findAll() {
        return medicoRepository.findAll();
    }

    @Override
    public Medico findById(Long id) {
        return medicoRepository.findById(id).orElseThrow(
                () -> new MedicoException("El medico con id " + id + " no se encuentra en la base de datos")
        );
    }

    @Override
    public Medico save(Medico medico) {
        return medicoRepository.save(medico);
    }

    @Override
    public List<AtencionMedicoDTO> findAtencionesById(Long medicoId) {
        // Agregamos esto en caso que no exista el medico que estamos buscado la app pueda realizar la excepcion
        Medico medico = this.findById(medicoId);
        // Con esto podemos obtener le listado de atenciones que posee el medico
        List<Atencion> atenciones = this.atencionClientRest.findByIdMedico(medico.getIdMedico());

        // De esta forma nos aseguramos que exista el listado de atenciones de un medico si no no tiene sentido
        // realizar el procesamiento de información
        if(!atenciones.isEmpty()){
            return atenciones.stream().map(atencion -> {
                Paciente paciente = null;
                try {
                    paciente = this.pacienteClientRest.findById(atencion.getIdPaciente());
                }catch (FeignException ex){
                    throw new MedicoException("Al momento de generar el listado de atenciones de pacientes, se" +
                            " encontro que el paciente con id " + atencion.getIdPaciente()+" no existe.");
                }

                PacienteDTO pacienteDTO = new PacienteDTO();
                pacienteDTO.setRun(paciente.getRun());
                pacienteDTO.setCorreo(paciente.getCorreo());
                pacienteDTO.setNombreCompleto(paciente.getNombres()+" "+paciente.getApellidos());
                try {
                    pacienteDTO.setPrevision(this.previsionClientRest.findById(paciente.getIdPrevision()).getNombre());
                }catch (FeignException ex){
                    throw new MedicoException("Al momento de generar el listado de atenciones de paciente se " +
                            "encontro el paciente con id "+ paciente.getIdPaciente()+" presenta una prevision " +
                            "que no existe y su id es "+ paciente.getIdPaciente());
                }
                pacienteDTO.setFechaNacimiento(paciente.getFechaNacimiento());


                AtencionMedicoDTO dto = new AtencionMedicoDTO();
                dto.setHoraAtencion(atencion.getHoraAtencion());
                dto.setComentario(atencion.getComentario());
                dto.setCosto(atencion.getCosto());
                dto.setPaciente(pacienteDTO);

                return dto;
            }).toList();
        }

        return List.of();
    }
}

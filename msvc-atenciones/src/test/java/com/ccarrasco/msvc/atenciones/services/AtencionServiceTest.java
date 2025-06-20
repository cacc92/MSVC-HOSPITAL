package com.ccarrasco.msvc.atenciones.services;

import com.ccarrasco.msvc.atenciones.clients.MedicoClientRest;
import com.ccarrasco.msvc.atenciones.clients.PacienteClientRest;
import com.ccarrasco.msvc.atenciones.clients.PrevisionClientRest;
import com.ccarrasco.msvc.atenciones.dtos.AtencionDTO;
import com.ccarrasco.msvc.atenciones.dtos.MedicoDTO;
import com.ccarrasco.msvc.atenciones.dtos.PacienteDTO;
import com.ccarrasco.msvc.atenciones.models.Medico;
import com.ccarrasco.msvc.atenciones.models.Paciente;
import com.ccarrasco.msvc.atenciones.models.Prevision;
import com.ccarrasco.msvc.atenciones.models.entities.Atencion;
import com.ccarrasco.msvc.atenciones.repositories.AtencionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AtencionServiceTest {

    @Mock
    private PacienteClientRest pacienteClientRest;

    @Mock
    private MedicoClientRest medicoClientRest;

    @Mock
    private AtencionRepository atencionRepository;

    @Mock
    private PrevisionClientRest previsionClientRest;

    @InjectMocks
    private AtencionServiceImpl atencionService;

    private Medico medicoTest;
    private Paciente pacienteTest;
    private Atencion atencionTest;
    private Prevision previsionTest;

    @BeforeEach
    public void setUp(){
        medicoTest = new Medico(
                1L,"18201315-3","Cesar Carrasco","Pediatra"
        );
        pacienteTest = new Paciente(
                1L,
                "18201315-3",
                "Cesar",
                "Carrasco",
                LocalDate.now(),
                "ccarrascocarre@gmail.com",
                1L
        );
        atencionTest = new Atencion(
                1L,
                LocalDateTime.now(),
                45000,
                "Test rutinario",
                1L,
                1L);

        previsionTest = new Prevision(
            1L,
            "ISAPRE"
        );


    }

    @Test
    @DisplayName("Debe crear una atencion")
    public void shouldCreateAtencion(){
        when(medicoClientRest.findById(1L)).thenReturn(this.medicoTest);
        when(pacienteClientRest.findById(1L)).thenReturn(this.pacienteTest);
        when(atencionRepository.save(any(Atencion.class))).thenReturn(this.atencionTest);

        Atencion result = atencionService.save(this.atencionTest);

        assertThat(result).isNotNull();
        assertThat(result).isEqualTo(this.atencionTest);

        verify(medicoClientRest,times(1)).findById(1L);
        verify(pacienteClientRest,times(1)).findById(1L);
        verify(atencionRepository, times(1)).save(any(Atencion.class));
    }

    @Test
    @DisplayName("Listar todas las atenciones")
    public void shouldListAllAtenciones(){
        List<Atencion> atenciones = new  ArrayList<>();
        atenciones.add(this.atencionTest);

        List<AtencionDTO> atencionDTOs = atenciones
                .stream()
                .map(a -> {

                    MedicoDTO medicoDTO = new MedicoDTO();
                    medicoDTO.setRunMedico(this.medicoTest.getRunMedico());
                    medicoDTO.setEspecialidad(this.medicoTest.getEspecialidad());
                    medicoDTO.setNombreCompleto(this.medicoTest.getNombreCompleto());

                    PacienteDTO pacienteDTO = new PacienteDTO();
                    pacienteDTO.setNombreCompleto(this.pacienteTest.getNombres()+" "+this.pacienteTest.getApellidos());
                    pacienteDTO.setCorreo(this.pacienteTest.getCorreo());
                    pacienteDTO.setPrevision(this.previsionTest.getNombre());
                    pacienteDTO.setFechaNacimiento(this.pacienteTest.getFechaNacimiento());
                    pacienteDTO.setRun(this.pacienteTest.getRun());

                    AtencionDTO atencionDTO = new AtencionDTO();



                    LocalDateTime local = a.getHoraAtencion();
                    String comentario =  a.getComentario();
                    Integer costo = a.getCosto();

                    atencionDTO.setHoraAtencion(local);
                    atencionDTO.setComentario(comentario);
                    atencionDTO.setCosto(costo);

                    atencionDTO.setMedico(medicoDTO);
                    atencionDTO.setPaciente(pacienteDTO);

                    return atencionDTO;

                }).toList();

        when(medicoClientRest.findById(1L)).thenReturn(this.medicoTest);
        when(pacienteClientRest.findById(1L)).thenReturn(this.pacienteTest);
        when(previsionClientRest.findById(1L)).thenReturn(this.previsionTest);
        when(atencionRepository.findAll()).thenReturn(atenciones);


        List<AtencionDTO> result = this.atencionService.findAll();

        assertThat(result).isNotNull();
        assertThat(result).hasSize(1);

        assertThat(result.get(0).getComentario()).isEqualTo(atencionDTOs.get(0).getComentario());
    }
}

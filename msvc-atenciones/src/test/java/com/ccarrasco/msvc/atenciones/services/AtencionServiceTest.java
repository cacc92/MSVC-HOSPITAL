package com.ccarrasco.msvc.atenciones.services;

import com.ccarrasco.msvc.atenciones.clients.MedicoClientRest;
import com.ccarrasco.msvc.atenciones.clients.PacienteClientRest;
import com.ccarrasco.msvc.atenciones.models.Medico;
import com.ccarrasco.msvc.atenciones.models.Paciente;
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

import static org.assertj.core.api.Assertions.assertThat;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AtencionServiceTest {

    @Mock
    private MedicoClientRest medicoClientRest;

    @Mock
    private PacienteClientRest pacienteClientRest;

    @Mock
    private AtencionRepository atencionRepository;

    @InjectMocks
    private AtencionServiceImpl atencionService;

    private Medico medicoTest;
    private Paciente pacienteTest;
    private Atencion atencionTest;

    @BeforeEach
    public void setUp(){
        medicoTest = new Medico();
        medicoTest.setRunMedico("18201315-3");
        medicoTest.setIdMedico(Long.valueOf(1L));
        medicoTest.setEspecialidad("pediatra");
        medicoTest.setNombreCompleto("Cesar Carrasco");

        pacienteTest = new Paciente();
        pacienteTest.setIdPaciente(Long.valueOf(1L));
        pacienteTest.setNombres("Cesar");
        pacienteTest.setApellidos("Carrasco");
        pacienteTest.setCorreo("ccarrascocarre@gmail.com");
        pacienteTest.setIdPrevision(Long.valueOf(1L));
        pacienteTest.setFechaNacimiento(LocalDate.now());
        pacienteTest.setRun("18201315-3");

        atencionTest = new Atencion();
        atencionTest.setIdAtencion(Long.valueOf(1L));
        atencionTest.setHoraAtencion(LocalDateTime.now());
        atencionTest.setComentario("Test Rutina");
        atencionTest.setCosto(Integer.valueOf(20000));
        atencionTest.setIdPaciente(Long.valueOf(1L));
        atencionTest.setIdMedico(Long.valueOf(1L));
    }

    @Test
    @DisplayName("Creacion de test")
    public void shouldCreateAtencion(){

        // Realiza las validaciones
        when(medicoClientRest.findById(Long.valueOf(1L))).thenReturn(medicoTest);
        when(pacienteClientRest.findById(Long.valueOf(1L))).thenReturn(pacienteTest);

        // Realiza el guardada
        when(atencionRepository.save(any(Atencion.class))).thenReturn(atencionTest);

        // Llamo al servicio
        Atencion result = atencionService.save(atencionTest);

        assertThat(result).isNotNull();
        assertThat(result).isEqualTo(atencionTest);

        verify(medicoClientRest, times(1)).findById(Long.valueOf(1L));
        verify(pacienteClientRest, times(1)).findById(Long.valueOf(1L));
        verify(atencionRepository, times(1)).save(any(Atencion.class));

    }


}

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

import static org.assertj.core.api.Assertions.as;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AtencionServiceTest {

    @Mock
    private PacienteClientRest pacienteClientRest;

    @Mock
    private MedicoClientRest medicoClientRest;

    @Mock
    private AtencionRepository atencionRepository;

    @InjectMocks
    private AtencionServiceImpl atencionService;

    private Medico medicoTest;
    private Paciente pacienteTest;
    private Atencion atencionTest;

    @BeforeEach
    public void setUp(){
        medicoTest = new Medico(
                1L,
                "1-1",
                "Dr house",
                "Pediatra"
        );

        pacienteTest = new Paciente(
                1L,
                "1-1",
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
                "Consulta general",
                1L,
                1L
        );
    }

    @Test
    @DisplayName("Se debe guardar una atención")
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
}

package com.ccarrasco.msvc.medicos.services;

import com.ccarrasco.msvc.medicos.exceptions.MedicoException;
import com.ccarrasco.msvc.medicos.models.entities.Medico;
import com.ccarrasco.msvc.medicos.repositories.MedicoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

// Habilitamos la integración de Mockito con JUnit 5.
@ExtendWith(MockitoExtension.class)
public class MedicoServiceTest {

    // Creamos un MOCK para la primera dependencia.
    @Mock
    private MedicoRepository medicoRepository;

    // Le decimos a Mockito que cree una instancia de PedidoService
    //    e inyecte el mock de arriba.
    @InjectMocks
    private MedicoServiceImpl medicoService;

    // Opcional: Generamos método que nos permita generar la informacion
    private Medico medicoDePrueba;

    // Opcional: Este método se ejecuta antes de cada prueba (@Test)
    @BeforeEach
    public void setUp() {
        this.medicoDePrueba = new Medico(
                1L, "11111111-1", "Dra. Ana Contreras", "PEDIATRIA"
        );
    }

    @Test
    @DisplayName("Debe listar todos los médicos")
    public void shouldFindAllMedicos() {
        // Preparamos el escenario : Donde agregamos otro medico para la consulta
        Medico otroMedico = new Medico(2L, "22222222-2", "Dr. Luis Soto", "CARDIOLOGIA");
        List<Medico> medicos = Arrays.asList(medicoDePrueba, otroMedico);

        // Cuando se llame a medicoRepository.findAll(), ENTONCES devuelve nuestra lista
        when(medicoRepository.findAll()).thenReturn(medicos);

        // Ahora ejecutamos el método del service
        List<Medico> result = medicoService.findAll();

        // Verificamos que se cumplan las condiciones
        assertThat(result).hasSize(2);
        assertThat(result).contains(medicoDePrueba, otroMedico);

        // Esto verifica que al final el medico repository para el findAll fue llamado solamente una vez
        verify(medicoRepository, times(1)).findAll();

    }

    @Test
    @DisplayName("Debe buscar un médico por su ID")
    public void shouldFindMedicoById() {
        // Se realiza el mock
        when(medicoRepository.findById(1L)).thenReturn(Optional.of(medicoDePrueba));
        Medico result = medicoService.findById(1L);
        assertThat(result).isNotNull();
        assertThat(result).isEqualTo(medicoDePrueba);
        verify(medicoRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("Debe devolver una exepcion si el médico no existe")
    public void shouldNotFindMedicoById() {
        Long idInexistente = 999L;
        when(medicoRepository.findById(idInexistente)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> {
            medicoService.findById(idInexistente);
        }).isInstanceOf(MedicoException.class)
                .hasMessageContaining("El medico con id " + idInexistente
                        + " no se encuentra en la base de datos");
        // Se verifica que el metodo de find by id sea llamado solamente una vez
        verify(medicoRepository, times(1)).findById(idInexistente);
    }

    @Test
    @DisplayName("Debe guardar un nuevo médico")
    public void shouldSaveMedico() {
        when(medicoRepository.save(any(Medico.class))).thenReturn(medicoDePrueba);
        Medico result = medicoService.save(medicoDePrueba);
        assertThat(result).isNotNull();
        assertThat(result).isEqualTo(medicoDePrueba);
        verify(medicoRepository, times(1)).save(any(Medico.class));
    }


}

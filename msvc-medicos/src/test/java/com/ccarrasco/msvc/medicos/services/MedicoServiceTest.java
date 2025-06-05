package com.ccarrasco.msvc.medicos.services;


import com.ccarrasco.msvc.medicos.exceptions.MedicoException;
import com.ccarrasco.msvc.medicos.models.entities.Medico;
import com.ccarrasco.msvc.medicos.repositories.MedicoRepository;
import net.datafaker.Faker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MedicoServiceTest {

    @Mock
    private MedicoRepository medicoRepository;


    @InjectMocks
    private MedicoServiceImpl medicoService;

    private Medico medicoPrueba;

    private List<Medico> medicos = new ArrayList<>();

    @BeforeEach
    public void setUp(){

        Faker faker = new Faker(Locale.of("es","CL"));
        for(int i=0;i<100;i++){
            Medico medico = new Medico();
            medico.setIdMedico((long) i);
            medico.setEspecialidad(faker.careProvider().medicalProfession());
            medico.setNombreCompleto(faker.name().fullName());

            String numeroString = faker.idNumber().valid().replaceAll("-","");
            String ultimo = numeroString.substring(numeroString.length()-1);
            String restante = numeroString.substring(0,numeroString.length()-1);
            medico.setRunMedico(restante+"-"+ultimo);

            this.medicos.add(medico);
        }
        this.medicoPrueba = new Medico(
                1L, "11111111-1", "Dra. Ana Contreras", "Pedriatra"
        );

    }

    @Test
    @DisplayName("Debe listar todos los médicos")
    public void shouldFindAllMedicos(){

        this.medicos.add(this.medicoPrueba);

        when(medicoRepository.findAll()).thenReturn(this.medicos);

        List<Medico> result = medicoService.findAll();

        assertThat(result).hasSize(101);
        assertThat(result).contains(this.medicoPrueba);

        verify(medicoRepository, times(1)).findAll();

    }

    @Test
    @DisplayName("Debe encontrar un medico por id")
    public void shouldFindMEdicoById(){
        when(medicoRepository.findById(1L)).thenReturn(Optional.of(this.medicoPrueba));
        Medico result = medicoService.findById(1L);
        assertThat(result).isNotNull();
        assertThat(result).isEqualTo(this.medicoPrueba);
        verify(medicoRepository,times(1)).findById(1L);
    }

    @Test
    @DisplayName("Debe entregar una excepcion cuando medico id no exista")
    public void shouldNotFindMedicoById(){
        Long idInexistente = 999L;
        when(medicoRepository.findById(idInexistente)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> {
            medicoService.findById(idInexistente);
        }).isInstanceOf(MedicoException.class)
                .hasMessageContaining("El medico con id " + idInexistente
                        + " no se encuentra en la base de datos");
        verify(medicoRepository, times(1)).findById(idInexistente);
    }

    @Test
    @DisplayName("Debe guardar un nuevo medico")
    public void shouldSaveMedico(){
        when(medicoRepository.save(any(Medico.class))).thenReturn(this.medicoPrueba);
        Medico result = medicoService.save(this.medicoPrueba);
        assertThat(result).isNotNull();
        assertThat(result).isEqualTo(this.medicoPrueba);
        verify(medicoRepository, times(1)).save(any(Medico.class));
    }
}

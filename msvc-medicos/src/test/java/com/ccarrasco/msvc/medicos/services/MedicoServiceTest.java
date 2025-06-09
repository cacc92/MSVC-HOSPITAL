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
        this.medicoPrueba = new Medico(
                "11111111-1", "Dra. Ana Contreras", "Pediatra"
        );
        Faker faker = new Faker(Locale.of("es","CL"));
        for(int i=0;i<100;i++){
            Medico medicoCreate = new Medico();

            String numeroStr = faker.idNumber().valid().replaceAll("-","");
            String ultimo = numeroStr.substring(numeroStr.length()-1);
            String restante = numeroStr.substring(0, numeroStr.length()-1);

            medicoCreate.setEspecialidad(faker.careProvider().medicalProfession());
            medicoCreate.setNombreCompleto(faker.name().fullName());

            medicoCreate.setRunMedico(restante+"-"+ultimo);
            medicos.add(medicoCreate);
        }
    }

    @Test
    @DisplayName("Debo listar todos los medicos")
    public void shouldFindAllMedicos(){

        List<Medico> medicos = this.medicos;
        medicos.add(medicoPrueba);
        when(medicoRepository.findAll()).thenReturn(medicos);

        List<Medico> result = medicoService.findAll();

        assertThat(result).hasSize(101);
        assertThat(result).contains(medicoPrueba);

        verify(medicoRepository, times(1)).findAll();

    }

    @Test
    @DisplayName("Debe buscar un medico")
    public void shouldFindById(){
        when(medicoRepository.findById(Long.valueOf(1L))).thenReturn(Optional.of(medicoPrueba));

        Medico result = medicoService.findById(Long.valueOf(1L));
        assertThat(result).isNotNull();
        assertThat(result).isEqualTo(medicoPrueba);
        verify(medicoRepository, times(1)).findById(Long.valueOf(1L));

    }

    @Test
    @DisplayName("Debe buscar un medico un I que n existe")
    public void shouldNotFindMedicoId(){
        Long idInexistente = (Long) 999L;
        when(medicoRepository.findById(idInexistente)).thenReturn(Optional.empty());
        assertThatThrownBy(()->{
            medicoService.findById(idInexistente);
        }).isInstanceOf(MedicoException.class)
                .hasMessageContaining("El medico con id " +
                        idInexistente + " no se encuentra en la base de datos");
        verify(medicoRepository, times(1)).findById(idInexistente);
    }

    @Test
    @DisplayName("Debe guardar un nuevo medico")
    public void shouldSaveMedico(){
        when(medicoRepository.save(any(Medico.class))).thenReturn(medicoPrueba);
        Medico result = medicoService.save(medicoPrueba);
        assertThat(result).isNotNull();
        assertThat(result).isEqualTo(medicoPrueba);
        verify(medicoRepository, times(1)).save(any(Medico.class));
    }


}

package com.ccarrasco.msvc.medicos.init;

import com.ccarrasco.msvc.medicos.models.entities.Medico;
import com.ccarrasco.msvc.medicos.repositories.MedicoRepository;
import net.datafaker.Faker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.Locale;
import java.util.Random;

@Profile("dev")
@Component
public class LoadDatabase implements CommandLineRunner {

    @Autowired
    private MedicoRepository medicoRepository;

    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

    @Override
    public void run(String... args) throws Exception {
        Faker faker = new Faker(Locale.of("es", "CL"));
        Random random = new Random();

        // en caso que no exista nada se crea el elemento
        if(medicoRepository.count() == 0) {

            for (int i = 0; i < 30; i++) {
                Medico medico = new Medico();

                // Con esto genero un numero fake de un rut
                String numeroStr = faker.idNumber().valid().replaceAll("-", "");
                String ultimo = numeroStr.substring(numeroStr.length() - 1);
                String restante = numeroStr.substring(0, numeroStr.length() - 1);

                // Se setean todas las propiedades
                medico.setRunMedico(restante + "-" + ultimo);
                medico.setEspecialidad(faker.careProvider().medicalProfession());
                medico.setNombreCompleto(faker.name().fullName());
                medico = medicoRepository.save(medico);

                log.info("Medico creado {}", medico);

            }
        }
    }
}

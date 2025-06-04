package com.ccarrasco.msvc.medicos.init;

import com.ccarrasco.msvc.medicos.models.entities.Medico;
import com.ccarrasco.msvc.medicos.repositories.MedicoRepository;
import net.datafaker.Faker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public class LoadDatabase implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

    @Autowired
    MedicoRepository medicoRepository;

    @Override
    public void run(String... args) throws Exception {
        Faker faker = new Faker(Locale.of("es","CL"));

        if(medicoRepository.count() == 0){
            for(int i=0;i<100;i++){
                Medico medico = new Medico();

                String numeroStr = faker.idNumber().valid().replaceAll("-","");
                String ultimo = numeroStr.substring(numeroStr.length()-1);
                String restante = numeroStr.substring(0, numeroStr.length()-1);

                medico.setEspecialidad(faker.careProvider().medicalProfession());
                medico.setNombreCompleto(faker.name().fullName());

                medico.setRunMedico(restante+"-"+ultimo);

                medico = medicoRepository.save(medico);
                log.info("El medico creado es {}", medico);
            }
        }

    }
}

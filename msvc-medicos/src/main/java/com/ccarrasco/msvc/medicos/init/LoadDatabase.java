package com.ccarrasco.msvc.medicos.init;

import com.ccarrasco.msvc.medicos.models.entities.Medico;
import com.ccarrasco.msvc.medicos.repositories.MedicoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LoadDatabase {

    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

    @Bean
    CommandLineRunner initDatabase(MedicoRepository medicoRepository) {
        return args -> {
            if(medicoRepository.count() == 0) {
                Medico cesar = new Medico("18201315-3","César Carrasco","Pediatra");
                Medico alexander = new Medico("18201315-1","Alexander Carré","Oncologo");

                log.info("Carga incial: {}", medicoRepository.save(cesar));
                log.info("Carga incial: {}", medicoRepository.save(alexander));
            }
        };
    }
}

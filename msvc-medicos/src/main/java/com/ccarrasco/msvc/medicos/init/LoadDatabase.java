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

@Profile("dev")
@Component
public class LoadDatabase implements CommandLineRunner {

    @Autowired
    private MedicoRepository medicoRepository;

    private static final Logger logger = LoggerFactory.getLogger(LoadDatabase.class);


    @Override
    public void run(String... args) throws Exception {
        Faker faker = new Faker(Locale.of("es","CL"));

        if(medicoRepository.count()==0){
            for(int i=0;i<1000;i++){
                Medico medico = new Medico();
                medico.setEspecialidad(faker.careProvider().medicalProfession());
                medico.setNombreCompleto(faker.name().fullName());

                String numeroString = faker.idNumber().valid().replaceAll("-","");
                String ultimo = numeroString.substring(numeroString.length()-1);
                String restante = numeroString.substring(0,numeroString.length()-1);

                medico.setRunMedico(restante+"-"+ultimo);
                logger.info("El rut que agregas es {}", medico.getRunMedico());
                medico = medicoRepository.save(medico);
                logger.info("EL medico creado es: {}", medico);

            }
        }

    }
}

package com.ccarrasco.msvc.medicos.controllers;

import com.ccarrasco.msvc.medicos.models.entities.Medico;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import net.minidev.json.JSONArray;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;


import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MedicoControllerTest {

    @Autowired
    TestRestTemplate restTemplate;

    @Test
    public void shouldReturnAllMedicoWhenListIsRequested(){
        ResponseEntity<String> response = restTemplate.getForEntity("/api/v1/medicos", String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        DocumentContext documentContext = JsonPath.parse(response.getBody());
        int medicosCount = documentContext.read("$.length()");
        assertThat(medicosCount).isEqualTo(2);

        JSONArray ids = documentContext.read("$..idMedico");
        assertThat(ids).containsExactlyInAnyOrder(1,2);

        JSONArray names = documentContext.read("$..nombreCompleto");
        assertThat(names).containsExactlyInAnyOrder("César Carrasco", "Alexander Carré");
    }

    @Test
    public void shouldReturnAnMedicoWhenFindById(){
        ResponseEntity<String> response = restTemplate.getForEntity("/api/v1/medicos/1", String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        DocumentContext documentContext = JsonPath.parse(response.getBody());
        Number idMedico = documentContext.read("$.idMedico");
        assertThat(idMedico).isEqualTo(1);

        String nombreCompleto = documentContext.read("$.nombreCompleto");
        assertThat(nombreCompleto).isEqualTo("César Carrasco");
    }

    @Test
    public void shouldReturnAnMedicoWithUnknownId(){
        ResponseEntity<String> response = restTemplate.getForEntity("/api/v1/medicos/9999", String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        DocumentContext documentContext = JsonPath.parse(response.getBody());
        Number status = documentContext.read("$.status");
        assertThat(status).isEqualTo(404);
    }

    @Test
    @DirtiesContext
    public void shouldCreateANewMedico(){
        Medico medico = new Medico("18201315-2","Alexander Carré","Oncologo");
        ResponseEntity<String> response = restTemplate.postForEntity("/api/v1/medicos",medico, String.class);
        DocumentContext documentContext = JsonPath.parse(response.getBody());
        Number idMedico = documentContext.read("$.idMedico");
        assertThat(idMedico).isEqualTo(3);
    }
}

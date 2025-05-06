package com.ccarrasco.msvc.pacientes.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter @Setter @ToString
@NoArgsConstructor @AllArgsConstructor
public class FichaPacienteCreationDTO {

    @NotBlank(message = "El campo datos personales no puede ser vacio")
    private String datosPersonales;

    @NotNull(message = "El camp de id paciente no puede estar vacio")
    private Long idPaciente;
}

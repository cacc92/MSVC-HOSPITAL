package com.ccarrasco.msvc.medicos.dtos;

import lombok.*;

import java.time.LocalDate;

@Getter @Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class PacienteDTO {

    private String run;
    private String nombreCompleto;
    private LocalDate fechaNacimiento;
    private String correo;
    private String prevision;

}

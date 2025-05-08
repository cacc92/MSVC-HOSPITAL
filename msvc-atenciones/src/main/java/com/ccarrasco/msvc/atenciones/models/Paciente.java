package com.ccarrasco.msvc.atenciones.models;

import lombok.*;

import java.time.LocalDate;

@Getter @Setter @ToString
@AllArgsConstructor @NoArgsConstructor
public class Paciente {

    private Long idPaciente;
    private String run;
    private String nombres;
    private String apellidos;
    private LocalDate fechaNacimiento;
    private String correo;
    private Long idPrevision;

}

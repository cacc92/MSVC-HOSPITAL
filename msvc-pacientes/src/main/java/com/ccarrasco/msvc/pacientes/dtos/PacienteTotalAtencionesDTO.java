package com.ccarrasco.msvc.pacientes.dtos;

import lombok.*;

@Getter @Setter @ToString
@AllArgsConstructor
@NoArgsConstructor
public class PacienteTotalAtencionesDTO {

    private String run;
    private String nombreCompleto;
    private String prevision;
    private double totalAtenciones;

}

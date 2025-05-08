package com.ccarrasco.msvc.atenciones.models;

import lombok.*;

@Getter @Setter @ToString
@NoArgsConstructor @AllArgsConstructor
public class Medico {
    private Long idMedico;
    private String runMedico;
    private String nombreCompleto;
    private String especialidad;
}

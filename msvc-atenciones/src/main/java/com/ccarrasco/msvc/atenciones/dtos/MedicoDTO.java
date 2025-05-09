package com.ccarrasco.msvc.atenciones.dtos;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class MedicoDTO {

    private String runMedico;
    private String nombreCompleto;
    private String especialidad;

}

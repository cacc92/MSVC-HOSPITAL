package com.ccarrasco.msvc.atenciones.dtos;

import com.ccarrasco.msvc.atenciones.models.Medico;
import com.ccarrasco.msvc.atenciones.models.Paciente;
import lombok.*;

import java.time.LocalDateTime;

@Getter @Setter @ToString
@NoArgsConstructor @AllArgsConstructor
public class AtencionMedicoDTO {

    private LocalDateTime horaAtencion;
    private Integer costo;
    private String comentario;
    private Paciente Paciente;

}

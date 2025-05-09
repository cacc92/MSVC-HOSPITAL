package com.ccarrasco.msvc.medicos.dtos;

import com.ccarrasco.msvc.atenciones.models.Medico;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class AtencionPacienteDTO {

    private LocalDateTime horaAtencion;
    private Integer costo;
    private String comentario;
    private MedicoDTO medico;


}

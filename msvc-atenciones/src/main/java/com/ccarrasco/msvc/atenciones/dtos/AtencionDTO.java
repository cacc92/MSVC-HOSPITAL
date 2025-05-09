package com.ccarrasco.msvc.atenciones.dtos;

import lombok.*;

import java.time.LocalDateTime;

@Getter @Setter @ToString
@NoArgsConstructor
@AllArgsConstructor
public class AtencionDTO {

    private LocalDateTime horaAtencion;
    private Integer costo;
    private String comentario;
    private PacienteDTO paciente;
    private MedicoDTO medico;
}

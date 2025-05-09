package com.ccarrasco.msvc.pacientes.models;

import lombok.*;

import java.time.LocalDateTime;

@Getter @Setter @ToString
@NoArgsConstructor
@AllArgsConstructor
public class Atencion {
    private Long idAtencion;
    private LocalDateTime horaAtencion;
    private Integer costo;
    private String comentario;
    private Long idPaciente;
    private Long idMedico;
}

package com.ccarrasco.msvc.medicos.models;

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

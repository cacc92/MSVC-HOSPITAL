package com.ccarrasco.msvc.medicos.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import java.time.LocalDateTime;


/**
 * Clase DTO para mostrar el listado de atenciones de un medico
 * Revisar que el medico tambien tiene un DTO, no se muestra la clase POJO
 * Recordar que los DTO son elementos que
 */
@Getter @Setter @ToString
@NoArgsConstructor @AllArgsConstructor
public class AtencionMedicoDTO {
    @JsonIgnore
    private Long idMedico;
    private LocalDateTime horaAtencion;
    private Integer costo;
    private String comentario;
    private PacienteDTO paciente;

}

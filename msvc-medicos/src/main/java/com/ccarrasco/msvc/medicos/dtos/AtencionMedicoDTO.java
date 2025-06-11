package com.ccarrasco.msvc.medicos.dtos;

import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;


/**
 * Clase DTO para mostrar el listado de atenciones de un medico
 * Revisar que el medico tambien tiene un DTO, no se muestra la clase POJO
 * Recordar que los DTO son elementos que
 */
@Getter @Setter @ToString
@NoArgsConstructor @AllArgsConstructor
@Schema(description = "DTO atencion medico")
public class AtencionMedicoDTO {

    @Schema(description = "Fecha de atencion", example = "2024-10-01T00:00:00")
    private LocalDateTime horaAtencion;
    @Schema(description = "Costo de atencion", example = "40000")
    private Integer costo;
    @Schema(description = "Comentario", example = "Esto es un comentario")
    private String comentario;

    //@ArraySchema(
    //        schema = @Schema(
    //                implementation = PacienteDTO.class
    //        )
    //)
    @Schema(
            description = "Este es el paciente con que se trabja",
            implementation = PacienteDTO.class
    )
    private PacienteDTO paciente;

}

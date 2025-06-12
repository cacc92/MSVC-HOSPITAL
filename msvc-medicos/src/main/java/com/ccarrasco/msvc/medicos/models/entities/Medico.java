package com.ccarrasco.msvc.medicos.models.entities;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Entity
@Table(name="medicos")
@Getter @Setter @NoArgsConstructor
@AllArgsConstructor @ToString
@Schema(description = "Entidad que representa un médico")
public class Medico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_medico")
    @Schema(description = "primary key de médico", example = "1")
    private Long idMedico;

    @Column(name = "run_medico", nullable = false)
    @NotBlank(message = "El campo run medico no puede ser vacio")
    @Pattern(regexp = "\\d{1,8}-[\\dKk]", message = "El formato del run medico debe ser XXXXXXXX-X")
    @Schema(description = "rut medico", example = "11222333-3")
    private String runMedico;

    @Column(name = "nombre_completo", nullable = false)
    @NotBlank(message = "El campo nombre completo medico no puede ser vacio")
    @Schema(description = "Nombre completo del medico", example = "Juan Carlos Bodoque")
    private String nombreCompleto;

    @Schema(description = "Especialidad del medico", example = "Pediatra")
    private String especialidad;

}

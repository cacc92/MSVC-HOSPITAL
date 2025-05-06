package com.ccarrasco.msvc.medicos.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Entity
@Table(name="medicos")
@Getter @Setter @NoArgsConstructor
@AllArgsConstructor @ToString
public class Medico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_medico")
    private Long idMedico;

    @Column(name = "run_medico", nullable = false)
    @NotBlank(message = "El campo run medico no puede ser vacio")
    @Pattern(regexp = "\\d{1,8}-[\\dKk]", message = "El formato del run medico debe ser XXXXXXXX-X")
    private String runMedico;

    @Column(name = "nombre_completo", nullable = false)
    @NotBlank(message = "El campo nombre completo medico no puede ser vacio")
    private String nombreCompleto;

    private String especialidad;

}

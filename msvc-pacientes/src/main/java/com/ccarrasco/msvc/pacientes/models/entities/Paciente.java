package com.ccarrasco.msvc.pacientes.models.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "pacientes")
@Getter @Setter @ToString
@NoArgsConstructor @AllArgsConstructor
public class Paciente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_paciente")
    private Long idPaciente;

    @Column(nullable = false, unique = true)
    @NotBlank(message = "El campo run paciente no puede ser vacio")
    @Pattern(regexp = "\\d{1,8}-[\\dKk]", message = "El formato del run paciente debe ser XXXXXXXX-X")
    private String run;

    @Column(nullable = false)
    @NotBlank(message = "El campo nombre paciente no puede ser vacio")
    private String nombres;

    @Column(nullable = false)
    @NotBlank(message = "El campo apellido paciente no puede ser vacio")
    private String apellidos;

    @Column(nullable = false)
    @NotNull(message = "El campo fecha nacimineto no puede ser vacio")
    private LocalDate fechaNacimiento;

    private String correo;

    @Column(nullable = false, name = "id_prevision")
    @NotNull(message = "El campo id_tipo_usuario no puede ser vacio")
    private Long idPrevision;
}

package com.ccarrasco.msvc.atenciones.models.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name="atenciones")
@Getter @Setter @ToString
@NoArgsConstructor @AllArgsConstructor
public class Atencion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_atencion")
    private Long idAtencion;

    @Column(name = "hora_atencion", nullable = false)
    @NotNull(message = "El campo hora de atencion no puede ser vacio")
    private LocalDateTime horaAtencion;

    @Column(nullable = false)
    @NotNull(message = "El campo costo no puede ser vacio")
    private Integer costo;

    private String comentario;


    @Column(name="id_paciente", nullable = false)
    @NotNull(message = "El campo de id_paciente no puede ser vacio")
    private Long idPaciente;

    @Column(name="id_medico", nullable = false)
    @NotNull(message = "El campo de id_medico no puede ser vacio")
    private Long idMedico;


}

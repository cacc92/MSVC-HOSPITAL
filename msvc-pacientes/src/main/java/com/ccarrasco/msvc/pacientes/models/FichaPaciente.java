package com.ccarrasco.msvc.pacientes.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Entity
@Table(name = "ficha_paciente")
@Getter @Setter @ToString
@NoArgsConstructor @AllArgsConstructor
public class FichaPaciente {

    @Id
    @Column(name="id_ficha_paciente")
    private Long idFichaPaciente;

    @Column(nullable = false, length = 250)
    @NotBlank(message = "El campo datos personales no puede ser vacio")
    private String datosPersonales;

    @OneToOne
    @MapsId
    @JoinColumn(name = "id_paciente", nullable = false)
    private Paciente paciente;

}

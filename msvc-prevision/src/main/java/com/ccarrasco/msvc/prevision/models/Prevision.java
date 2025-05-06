package com.ccarrasco.msvc.prevision.models;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Table(name = "previsiones")
@Getter @Setter @ToString
@AllArgsConstructor @NoArgsConstructor
public class Prevision {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_prevision")
    private Long idPrevision;

    @Column(nullable = false, unique = true)
    @NotEmpty(message = "El nombre de la previsión no puede ser vacio")
    private String nombre;

}

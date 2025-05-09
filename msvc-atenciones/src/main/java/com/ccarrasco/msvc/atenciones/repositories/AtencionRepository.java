package com.ccarrasco.msvc.atenciones.repositories;

import com.ccarrasco.msvc.atenciones.models.entities.Atencion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AtencionRepository extends JpaRepository<Atencion, Long> {

    List<Atencion> findByIdPaciente(Long idPaciente);

    List<Atencion> findByIdMedico(Long idMedico);

}

package com.ccarrasco.msvc.atenciones.repositories;

import com.ccarrasco.msvc.atenciones.models.Atencion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AtencionRepository extends JpaRepository<Atencion, Long> {
}

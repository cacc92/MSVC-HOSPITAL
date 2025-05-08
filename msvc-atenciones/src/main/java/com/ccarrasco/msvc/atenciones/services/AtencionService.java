package com.ccarrasco.msvc.atenciones.services;

import com.ccarrasco.msvc.atenciones.models.Atencion;

import java.util.List;

public interface AtencionService {

    List<Atencion> findAll();
    Atencion findById(Long id);
    Atencion save(Atencion atencion);
    List<Atencion> findByIdPaciente(Long idPaciente);
    List<Atencion> findByIdMedico(Long idMedico);

}

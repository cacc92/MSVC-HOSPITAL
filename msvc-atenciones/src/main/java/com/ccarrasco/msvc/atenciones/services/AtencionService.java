package com.ccarrasco.msvc.atenciones.services;

import com.ccarrasco.msvc.atenciones.dtos.AtencionDTO;
import com.ccarrasco.msvc.atenciones.models.Atencion;

import java.util.List;

public interface AtencionService {

    List<AtencionDTO> findAll();
    Atencion findById(Long id);
    Atencion save(Atencion atencion);
    List<Atencion> findByMedicoId(Long medicoId);
    List<Atencion> findByPacienteId(Long pacienteId);

}

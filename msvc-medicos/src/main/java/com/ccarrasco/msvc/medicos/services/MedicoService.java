package com.ccarrasco.msvc.medicos.services;

import com.ccarrasco.msvc.medicos.models.Medico;

import java.util.List;

public interface MedicoService {
    List<Medico> findAll();
    Medico findById(Long id);
    Medico save(Medico medico);
}

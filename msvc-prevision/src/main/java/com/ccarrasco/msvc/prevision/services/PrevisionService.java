package com.ccarrasco.msvc.prevision.services;

import com.ccarrasco.msvc.prevision.models.Prevision;

import java.util.List;

public interface PrevisionService {
    List<Prevision> findAll();
    Prevision findById(Long id);
    Prevision save(Prevision prevision);
}

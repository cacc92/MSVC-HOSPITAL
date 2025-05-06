package com.ccarrasco.msvc.prevision.services;

import com.ccarrasco.msvc.prevision.exceptions.PrevisionException;
import com.ccarrasco.msvc.prevision.models.Prevision;
import com.ccarrasco.msvc.prevision.repositories.PrevisionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PrevisionServiceImpl implements PrevisionService {
    @Autowired
    private PrevisionRepository previsionRepository;

    @Override
    public List<Prevision> findAll() {
        return this.previsionRepository.findAll();
    }

    @Override
    public Prevision findById(Long id) {
        return this.previsionRepository.findById(id).orElseThrow(
                () -> new PrevisionException("La prevision con el id: " + id+" no se encuentra en la base de datos")
        );
    }

    @Override
    public Prevision save(Prevision prevision) {
        if(this.previsionRepository.findByNombre(prevision.getNombre()).isPresent()) {
            throw new PrevisionException("La prevision con el nombre: " + prevision.getNombre()
                    + " ya existe en la base de datos");
        }
        return this.previsionRepository.save(prevision);
    }
}

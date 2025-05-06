package com.ccarrasco.msvc.atenciones.services;

import com.ccarrasco.msvc.atenciones.exceptions.AtencionException;
import com.ccarrasco.msvc.atenciones.models.Atencion;
import com.ccarrasco.msvc.atenciones.repositories.AtencionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AtencionServiceImpl implements AtencionService {

    @Autowired
    private AtencionRepository atencionRepository;

    @Override
    public List<Atencion> findAll() {
        return this.atencionRepository.findAll();
    }

    @Override
    public Atencion findById(Long id) {
        return this.atencionRepository.findById(id).orElseThrow(
                () -> new AtencionException("El atencion con id: " + id + " no se encuentra en la base de datos")
        );
    }

    @Override
    public Atencion save(Atencion atencion) {
        return this.atencionRepository.save(atencion);
    }
}

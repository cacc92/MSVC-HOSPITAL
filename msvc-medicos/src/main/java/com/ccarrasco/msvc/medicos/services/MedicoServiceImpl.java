package com.ccarrasco.msvc.medicos.services;

import com.ccarrasco.msvc.medicos.exceptions.MedicoException;
import com.ccarrasco.msvc.medicos.models.Medico;
import com.ccarrasco.msvc.medicos.repositories.MedicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MedicoServiceImpl implements MedicoService {

    @Autowired
    private MedicoRepository medicoRepository;

    @Override
    public List<Medico> findAll() {
        return medicoRepository.findAll();
    }

    @Override
    public Medico findById(Long id) {
        return medicoRepository.findById(id).orElseThrow(
                () -> new MedicoException("El medico con id " + id + " no se encuentra en la base de datos")
        );
    }

    @Override
    public Medico save(Medico medico) {
        return medicoRepository.save(medico);
    }
}

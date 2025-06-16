package com.ccarrasco.msvc.medicos.assemblers;

import com.ccarrasco.msvc.medicos.controllers.MedicoControllerV2;
import com.ccarrasco.msvc.medicos.dtos.AtencionMedicoDTO;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class AtencionMedicoDTOModelAssembler implements RepresentationModelAssembler<AtencionMedicoDTO, EntityModel<AtencionMedicoDTO>> {
    @Override
    public EntityModel<AtencionMedicoDTO> toModel(AtencionMedicoDTO entity) {
        return EntityModel.of(
                entity,
                linkTo(methodOn(MedicoControllerV2.class).findAtencionesById(entity.getIdMedico())).withRel("atenciones-medico")
        );
    }
}

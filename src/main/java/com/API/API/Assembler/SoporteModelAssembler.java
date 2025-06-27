package com.API.API.Assembler;

import com.API.API.controller.SoporteController;
import com.API.API.model.Soporte;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class SoporteModelAssembler implements RepresentationModelAssembler<Soporte, EntityModel<Soporte>> {

    @Override
    @NonNull
    public EntityModel<Soporte> toModel(@NonNull Soporte soporte) {
        return EntityModel.of(soporte,
                linkTo(methodOn(SoporteController.class).getSoporte(soporte.getId())).withSelfRel(),
                linkTo(methodOn(SoporteController.class).listar()).withRel("soportes"),
                linkTo(methodOn(SoporteController.class).updateSoporte(soporte.getId(), null)).withRel("update"),
                linkTo(methodOn(SoporteController.class).deleteSoporte(soporte.getId())).withRel("delete")
        );
    }
}

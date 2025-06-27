package com.API.API.Assembler;

import com.API.API.controller.GerenteDeCursosController;
import com.API.API.model.Gerente;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class GerenteModelAssembler implements RepresentationModelAssembler<Gerente, EntityModel<Gerente>> {

    @Override
    @NonNull
    public EntityModel<Gerente> toModel(@NonNull Gerente gerente) {
        return EntityModel.of(gerente,
                linkTo(methodOn(GerenteDeCursosController.class).getGerenteDeCursos(gerente.getId())).withSelfRel(),
                linkTo(methodOn(GerenteDeCursosController.class).getGerenteDeCursos()).withRel("gerentes"),
                linkTo(methodOn(GerenteDeCursosController.class).updateGerenteDeCursos(gerente.getId(), null)).withRel("update"),
                linkTo(methodOn(GerenteDeCursosController.class).deleteGerenteDeCursos(gerente.getId())).withRel("delete")
        );
    }
}

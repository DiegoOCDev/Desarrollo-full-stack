package com.API.API.Assembler;

import com.API.API.controller.EstudianteController;
import com.API.API.model.Estudiante;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class EstudianteModelAssembler implements RepresentationModelAssembler<Estudiante, EntityModel<Estudiante>> {

    @Override
    @NonNull
    public EntityModel<Estudiante> toModel(@NonNull Estudiante estudiante) {
        return EntityModel.of(estudiante,
                linkTo(methodOn(EstudianteController.class).getEstudiante(estudiante.getId())).withSelfRel(),
                linkTo(methodOn(EstudianteController.class).getEstudiantes()).withRel("estudiantes"),
                linkTo(methodOn(EstudianteController.class).updateEstudiante(estudiante.getId(), null)).withRel("update"),
                linkTo(methodOn(EstudianteController.class).deleteEstudiante(estudiante.getId())).withRel("delete")
        );
    }
}

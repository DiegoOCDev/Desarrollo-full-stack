package com.API.API.Assembler;


import com.API.API.controller.CursoController;
import com.API.API.model.Curso;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class CursoModelAssembler implements RepresentationModelAssembler<Curso, EntityModel<Curso>> {

    @Override
    @NonNull
    public EntityModel<Curso> toModel(@NonNull Curso curso) {
        return EntityModel.of(curso,
                linkTo(methodOn(CursoController.class).getCurso(curso.getId())).withSelfRel(),
                linkTo(methodOn(CursoController.class).getCursos()).withRel("cursos"),
                linkTo(methodOn(CursoController.class).updateCurso(curso.getId(), null)).withRel("update"),
                linkTo(methodOn(CursoController.class).deleteCurso(curso.getId())).withRel("delete")
        );
    }
}

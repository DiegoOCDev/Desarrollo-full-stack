package com.API.API.Assembler;

import com.API.API.controller.EvaluacionController;
import com.API.API.model.Evaluacion;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class EvaluacionModelAssembler implements RepresentationModelAssembler<Evaluacion, EntityModel<Evaluacion>> {

    @Override
    @NonNull
    public EntityModel<Evaluacion> toModel(@NonNull Evaluacion evaluacion) {
        return EntityModel.of(evaluacion,
                linkTo(methodOn(EvaluacionController.class).getEvaluacion(evaluacion.getId())).withSelfRel(),
                linkTo(methodOn(EvaluacionController.class).getEvaluacions()).withRel("evaluaciones"),
                linkTo(methodOn(EvaluacionController.class).updateEvaluacion(evaluacion.getId(), null)).withRel("update"),
                linkTo(methodOn(EvaluacionController.class).deleteEvaluacion(evaluacion.getId())).withRel("delete")
        );
    }
}

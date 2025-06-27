package com.API.API.Assembler;

import com.API.API.controller.ReporteController;
import com.API.API.model.Reporte;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class ReporteModelAssembler implements RepresentationModelAssembler<Reporte, EntityModel<Reporte>> {

    @Override
    @NonNull
    public EntityModel<Reporte> toModel(@NonNull Reporte reporte) {
        return EntityModel.of(reporte,
                linkTo(methodOn(ReporteController.class).getReporteById(reporte.getId())).withSelfRel(),
                linkTo(methodOn(ReporteController.class).getReportes()).withRel("reportes"),
                linkTo(methodOn(ReporteController.class).updateReporte(reporte.getId(), null)).withRel("update"),
                linkTo(methodOn(ReporteController.class).deleteReporte(reporte.getId())).withRel("delete")
        );
    }
}
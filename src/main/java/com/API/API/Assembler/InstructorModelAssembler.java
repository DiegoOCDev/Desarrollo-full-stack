package com.API.API.Assembler;

import com.API.API.controller.InstructorController;
import com.API.API.model.Instructor;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class InstructorModelAssembler implements RepresentationModelAssembler<Instructor, EntityModel<Instructor>> {

    @Override
    @NonNull
    public EntityModel<Instructor> toModel(@NonNull Instructor instructor) {
        return EntityModel.of(instructor,
                linkTo(methodOn(InstructorController.class).getInstructorById(instructor.getId())).withSelfRel(),
                linkTo(methodOn(InstructorController.class).getAllInstructors()).withRel("instructores"),
                linkTo(methodOn(InstructorController.class).updateInstructor(instructor.getId(), null)).withRel("update"),
                linkTo(methodOn(InstructorController.class).deleteInstructor(instructor.getId())).withRel("delete")
        );
    }
}

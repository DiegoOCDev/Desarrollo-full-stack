package com.API.API.Assembler;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import com.API.API.controller.AdminController;
import com.API.API.model.Admin;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class AdminModelAssembler implements RepresentationModelAssembler<Admin, EntityModel<Admin>> {

    @Override
    @NonNull
    public EntityModel<Admin> toModel(@NonNull Admin admin) {
        return EntityModel.of(admin,
                linkTo(methodOn(AdminController.class).getAdmin(admin.getId())).withSelfRel(),
                linkTo(methodOn(AdminController.class).getAdmins()).withRel("admins"),
                linkTo(methodOn(AdminController.class).updateAdmin(admin.getId(), admin)).withRel("update"),
                linkTo(methodOn(AdminController.class).deleteAdmin(admin.getId())).withRel("delete")
        );
    }
}
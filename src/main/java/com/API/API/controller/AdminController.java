package com.API.API.controller;

import com.API.API.model.Admin;
import com.API.API.model.Curso;
import com.API.API.service.AdminService;
import io.swagger.v3.oas.annotations.*;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/Admins")
@Tag(name = "Controlador Admins", description = "API para la gestión de administradores")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @GetMapping
    @Operation(summary = "Obtener todos los administradores", description = "Devuelve la lista completa de administradores")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de administradores obtenida",
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = Admin.class)))),
            @ApiResponse(responseCode = "404", description = "No se encontraron administradores",content = @Content)
    })
    public ResponseEntity<List<Admin>> getAdmins() {
        List<Admin> admins = adminService.getAllAdmins();
        if (admins.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(admins, HttpStatus.OK);
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener administrador por ID", description = "Devuelve un administrador específico por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Administrador encontrado",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Curso.class))),
            @ApiResponse(responseCode = "404", description = "Administrador no encontrado", content = @Content)
    })
    public ResponseEntity<Admin> getAdmin(
            @Parameter(description = "ID del administrador a buscar", example = "1")
            @PathVariable int id) {
        Admin result = adminService.getAdmin(id);
        if (result == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(result, HttpStatus.OK);
        }
    }

    @PostMapping
    @Operation(summary = "Agregar administrador", description = "Crea un nuevo administrador con la información proporcionada")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Administrador creado exitosamente",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Admin.class))),
            @ApiResponse(responseCode = "400", description = "Datos inválidos", content = @Content)
    })
    public ResponseEntity<Admin> addAdmin(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Datos del administrador a crear",
                    required = true,
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Admin.class)))
            @RequestBody Admin admin) {
        Admin creado = adminService.addAdmin(admin);
        return new ResponseEntity<>(creado, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar administrador", description = "Actualiza los datos de un administrador existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Administrador actualizado correctamente",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "404", description = "Administrador no encontrado", content = @Content)
    })
    public ResponseEntity<String> updateAdmin(
            @Parameter(description = "ID del administrador a actualizar", example = "1")
            @PathVariable int id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Datos del administrador actualizados",
                    required = true,
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Admin.class)))
            @RequestBody Admin admin) {
        String result = adminService.updateAdmin(id, admin);
        if (result.trim().startsWith("No se encuentra")) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(result, HttpStatus.OK);
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar administrador por ID", description = "Elimina un administrador específico usando su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Administrador eliminado correctamente"),
            @ApiResponse(responseCode = "404", description = "Administrador no encontrado")
    })
    public ResponseEntity<String> deleteAdmin(
            @Parameter(description = "ID del administrador a eliminar", example = "1")
            @PathVariable int id) {
        String result = adminService.deleteAdmin(id);
        if (result.equals("No se encuentra")) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(result, HttpStatus.OK);
        }
    }
}

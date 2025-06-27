package com.API.API.controller;

import com.API.API.model.Curso;
import com.API.API.model.Evaluacion;
import com.API.API.model.Instructor;
import com.API.API.service.InstructorService;
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
@RequestMapping("/Instructors")
@Tag(name = "Controlador Instructores", description = "API para la gestión de instructores en el sistema")
public class InstructorController {

    @Autowired
    private InstructorService instructorService;

    @GetMapping
    @Operation(summary = "Obtener todos los Instructores", description = "Devuelve la lista completa de instructores registrados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de instructores obtenida",
                    content = @Content(mediaType = "application/json",  array = @ArraySchema(schema = @Schema(implementation = Instructor.class)))),
            @ApiResponse(responseCode = "404", description = "No se encontraron instructores", content = @Content)
    })
    public ResponseEntity<List<Instructor>> getAllInstructors() {
        List<Instructor> instructores = instructorService.getAllIntructores();
        if (instructores.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(instructores, HttpStatus.OK);
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener Instructor por ID", description = "Devuelve los detalles de un instructor específico por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Instructor encontrado",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Instructor.class))),
            @ApiResponse(responseCode = "404", description = "Instructor no encontrado",content = @Content)
    })
    public ResponseEntity<Instructor> getInstructorById(
            @Parameter(description = "ID del instructor a buscar", example = "1")
            @PathVariable int id) {
        Instructor result = instructorService.getInstructor(id);
        if (result == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(result, HttpStatus.OK);
        }
    }

    @PostMapping
    @Operation(summary = "Agregar Instructor", description = "Permite registrar un nuevo instructor en el sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Instructor creado exitosamente",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Instructor.class))),
            @ApiResponse(responseCode = "400", description = "Datos inválidos", content = @Content)
    })
    public ResponseEntity<Instructor> addInstructor(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Datos del instructor a crear",
                    required = true,
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Instructor.class)))
            @RequestBody Instructor instructor) {
        Instructor creado = instructorService.addInstructor(instructor);
        return new ResponseEntity<>(creado, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar Instructor", description = "Permite actualizar la información de un instructor existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Instructor actualizado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Instructor no encontrado")
    })
    public ResponseEntity<String> updateInstructor(
            @Parameter(description = "ID del instructor a actualizar", example = "1")
            @PathVariable int id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Datos actualizados del instructor",
                    required = true,
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Instructor.class)))
            @RequestBody Instructor instructor) {
        String result = instructorService.updateInstructor(id, instructor);
        if (result.trim().startsWith("No se encuentra")) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(result, HttpStatus.OK);
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar Instructor por ID", description = "Elimina un instructor específico usando su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Instructor eliminado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Instructor no encontrado")
    })
    public ResponseEntity<String> deleteInstructor(
            @Parameter(description = "ID del instructor a eliminar", example = "1")
            @PathVariable int id) {
        String result = instructorService.deleteInstructor(id);
        if (result.equals("No se encuentra")) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(result, HttpStatus.OK);
        }
    }
}

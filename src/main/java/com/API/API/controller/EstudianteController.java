package com.API.API.controller;

import com.API.API.model.Admin;
import com.API.API.model.Curso;
import com.API.API.model.Estudiante;
import com.API.API.service.EstudianteService;
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
@RequestMapping("/Estudiantes")
@Tag(name = "Controlador Estudiantes", description = "API para la gestión de estudiantes en el sistema")
public class EstudianteController {

    @Autowired
    private EstudianteService estudianteService;

    @GetMapping
    @Operation(summary = "Obtener todos los estudiantes", description = "Devuelve la lista completa de estudiantes registrados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de estudiantes obtenida exitosamente",
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = Estudiante.class)))),
            @ApiResponse(responseCode = "404", description = "No se encontraron estudiantes", content = @Content)
    })
    public ResponseEntity<List<Estudiante>> getEstudiantes() {
        List<Estudiante> estudiantes = estudianteService.getAllEstudiantes();
        if (estudiantes.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(estudiantes, HttpStatus.OK);
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener estudiante por ID", description = "Devuelve los detalles de un estudiante específico por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Estudiante encontrado",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Estudiante.class))),

            @ApiResponse(responseCode = "404", description = "Estudiante no encontrado",content = @Content)
    })
    public ResponseEntity<Estudiante> getEstudiante(
            @Parameter(description = "ID del estudiante a buscar", example = "1")
            @PathVariable int id) {
        Estudiante result = estudianteService.getEstudiante(id);
        if (result == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(result, HttpStatus.OK);
        }
    }

    @PostMapping
    @Operation(summary = "Agregar estudiante", description = "Permite registrar un nuevo estudiante en el sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Estudiante creado exitosamente",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Estudiante.class))),
            @ApiResponse(responseCode = "400", description = "Datos inválidos", content = @Content)
    })
    public ResponseEntity<Estudiante> addEstudiante(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Datos del estudiante a crear",
                    required = true,
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Estudiante.class)))
            @RequestBody Estudiante estudiante) {
        Estudiante creado = estudianteService.addEstudiante(estudiante);
        return new ResponseEntity<>(creado, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar estudiante", description = "Permite actualizar la información de un estudiante existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Estudiante actualizado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Estudiante no encontrado")
    })
    public ResponseEntity<String> updateEstudiante(
            @Parameter(description = "ID del estudiante a actualizar", example = "1")
            @PathVariable int id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Datos del estudiante actualizados",
                    required = true,
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Estudiante.class)))
            @RequestBody Estudiante estudiante) {
        String result = estudianteService.updateEstudiante(id, estudiante);
        if (result.trim().startsWith("No se encuentra")) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(result, HttpStatus.OK);
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar estudiante por ID", description = "Elimina un estudiante específico usando su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Estudiante eliminado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Estudiante no encontrado")
    })
    public ResponseEntity<String> deleteEstudiante(
            @Parameter(description = "ID del estudiante a eliminar", example = "1")
            @PathVariable int id) {
        String result = estudianteService.deleteEstudiante(id);
        if (result.equals("No se encuentra")) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(result, HttpStatus.OK);
        }
    }
}

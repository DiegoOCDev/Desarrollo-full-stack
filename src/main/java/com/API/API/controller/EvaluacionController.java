package com.API.API.controller;

import com.API.API.model.Curso;
import com.API.API.model.Estudiante;
import com.API.API.model.Evaluacion;
import com.API.API.service.EvaluacionService;
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
@RequestMapping("/Evaluacions")
@Tag(name = "Controlador Evaluaciones", description = "API para la gestión de evaluaciones de cursos")
public class EvaluacionController {

    @Autowired
    private EvaluacionService evaluacionService;

    @GetMapping
    @Operation(summary = "Obtener todas las evaluaciones", description = "Devuelve la lista completa de evaluaciones registradas")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de evaluaciones obtenida exitosamente",
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = Evaluacion.class)))),
            @ApiResponse(responseCode = "404", description = "No se encontraron evaluaciones", content = @Content)
    })
    public ResponseEntity<List<Evaluacion>> getEvaluacions() {
        List<Evaluacion> evaluaciones = evaluacionService.getAllEvaluaciones();
        if (evaluaciones.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(evaluaciones, HttpStatus.OK);
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener evaluación por ID", description = "Devuelve los detalles de una evaluación específica por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Evaluación encontrada",
                    content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Evaluacion.class))),
            @ApiResponse(responseCode = "404", description = "Evaluación no encontrada",content = @Content)
    })
    public ResponseEntity<Evaluacion> getEvaluacion(
            @Parameter(description = "ID de la evaluación a buscar", example = "1")
            @PathVariable int id) {
        Evaluacion result = evaluacionService.getEvaluacion(id);
        if (result == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(result, HttpStatus.OK);
        }
    }

    @PostMapping
    @Operation(summary = "Agregar evaluación", description = "Permite registrar una nueva evaluación en el sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Evaluación creada exitosamente",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Evaluacion.class))),
            @ApiResponse(responseCode = "400", description = "Datos inválidos", content = @Content)
    })
    public ResponseEntity<Evaluacion> addEvaluacion(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Datos de la evaluación a crear",
                    required = true,
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Evaluacion.class)))
            @RequestBody Evaluacion evaluacion) {
        Evaluacion creada = evaluacionService.addEvaluacion(evaluacion);
        return new ResponseEntity<>(creada, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar evaluación", description = "Permite actualizar la información de una evaluación existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Evaluación actualizada exitosamente"),
            @ApiResponse(responseCode = "404", description = "Evaluación no encontrada")
    })
    public ResponseEntity<String> updateEvaluacion(
            @Parameter(description = "ID de la evaluación a actualizar", example = "1")
            @PathVariable int id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Datos actualizados de la evaluación",
                    required = true,
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Evaluacion.class)))
            @RequestBody Evaluacion evaluacion) {
        String result = evaluacionService.updateEvaluacion(id, evaluacion);
        if (result.trim().startsWith("No se encuentra")) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(result, HttpStatus.OK);
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar evaluación por ID", description = "Elimina una evaluación específica usando su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Evaluación eliminada exitosamente"),
            @ApiResponse(responseCode = "404", description = "Evaluación no encontrada")
    })
    public ResponseEntity<String> deleteEvaluacion(
            @Parameter(description = "ID de la evaluación a eliminar", example = "1")
            @PathVariable int id) {
        String result = evaluacionService.deleteEvaluacion(id);
        if (result.equals("No se encuentra")) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(result, HttpStatus.OK);
        }
    }
}

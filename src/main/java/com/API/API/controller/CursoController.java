package com.API.API.controller;

import com.API.API.model.Admin;
import com.API.API.model.Curso;
import com.API.API.service.CursoService;
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
@RequestMapping("/Cursos")
@Tag(name = "Controlador Cursos", description = "API para la gestión de cursos en el sistema")
public class CursoController {

    @Autowired
    private CursoService cursoService;

    @GetMapping
    @Operation(summary = "Obtener todos los cursos", description = "Devuelve la lista completa de cursos registrados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de cursos obtenida exitosamente",
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = Curso.class)))),
            @ApiResponse(responseCode = "404", description = "No se encontraron cursos", content = @Content)
    })
    public ResponseEntity<List<Curso>> getCursos() {
        List<Curso> cursos = cursoService.getAllCursos();
        if (cursos.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(cursos, HttpStatus.OK);
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener curso por ID", description = "Devuelve los detalles de un curso específico por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Curso encontrado",

                    content = @Content(mediaType = "application/json",
                        schema = @Schema(implementation = Curso.class))),

            @ApiResponse(responseCode = "404", description = "Curso no encontrado",content = @Content)
    })
    public ResponseEntity<Curso> getCurso(
            @Parameter(description = "ID del curso a buscar", example = "1")
            @PathVariable Integer id) {
        Curso result = cursoService.getCurso(id);
        if (result == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(result, HttpStatus.OK);
        }
    }

    @PostMapping
    @Operation(summary = "Agregar curso", description = "Permite registrar un nuevo curso en el sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Curso creado exitosamente",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Curso.class))),
            @ApiResponse(responseCode = "400", description = "Datos inválidos", content = @Content)
    })
    public ResponseEntity<Curso> addCurso(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Datos del curso a crear",
                    required = true,
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Curso.class)))
            @RequestBody Curso curso) {
        Curso creado = cursoService.addCurso(curso);
        return new ResponseEntity<>(creado, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar curso por ID", description = "Elimina un curso específico usando su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Curso eliminado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Curso no encontrado")
    })
    public ResponseEntity<String> deleteCurso(
            @Parameter(description = "ID del curso a eliminar", example = "1")
            @PathVariable Integer id) {
        String result = cursoService.deleteCurso(id);
        if (result.equals("No se encuentra")) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(result, HttpStatus.OK);
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar curso", description = "Permite actualizar la información de un curso existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Curso actualizado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Curso no encontrado")
    })
    public ResponseEntity<String> updateCurso(
            @Parameter(description = "ID del curso a actualizar", example = "1")
            @PathVariable Integer id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Datos del curso actualizados",
                    required = true,
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Curso.class)))
            @RequestBody Curso curso) {
        String result = cursoService.updateCurso(id, curso);
        if (result.trim().startsWith("No se encuentra")) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(result, HttpStatus.OK);
        }
    }
}

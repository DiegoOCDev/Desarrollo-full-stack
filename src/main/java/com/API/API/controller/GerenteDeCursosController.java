package com.API.API.controller;

import com.API.API.model.Curso;
import com.API.API.model.Evaluacion;
import com.API.API.model.Gerente;
import com.API.API.service.GerenteService;
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
@RequestMapping("/GerenteDeCursos")
@Tag(name = "Controlador Gerente de Cursos", description = "API para la gestión de gerentes de cursos")
public class GerenteDeCursosController {

    @Autowired
    private GerenteService gerenteService;

    @GetMapping
    @Operation(summary = "Obtener todos los Gerentes de Curso", description = "Devuelve la lista completa de gerentes de curso registrados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de gerentes obtenida exitosamente",
                    content = @Content(mediaType = "application/json",  array = @ArraySchema(schema = @Schema(implementation = Gerente.class)))),
            @ApiResponse(responseCode = "404", description = "No se encontraron gerentes", content = @Content),
    })
    public ResponseEntity<List<Gerente>> getGerenteDeCursos() {
        List<Gerente> gerentes = gerenteService.getAllGerentes();
        if (gerentes.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(gerentes, HttpStatus.OK);
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener Gerente de Curso por ID", description = "Devuelve los detalles de un gerente de curso específico por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Gerente encontrado",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Gerente.class))),
            @ApiResponse(responseCode = "404", description = "Gerente no encontrado",content = @Content)
    })
    public ResponseEntity<Gerente> getGerenteDeCursos(
            @Parameter(description = "ID del gerente de curso a buscar", example = "1")
            @PathVariable int id) {
        Gerente result = gerenteService.getGerente(id);
        if (result == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(result, HttpStatus.OK);
        }
    }

    @PostMapping
    @Operation(summary = "Agregar Gerente de Curso", description = "Permite registrar un nuevo gerente de curso en el sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Gerente creado exitosamente",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Gerente.class))),
            @ApiResponse(responseCode = "400", description = "Datos inválidos", content = @Content)
    })
    public ResponseEntity<Gerente> addGerenteDeCursos(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Datos del gerente de curso a crear",
                    required = true,
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Gerente.class)))
            @RequestBody Gerente gerenteDeCursos) {
        Gerente creado = gerenteService.addGerente(gerenteDeCursos);
        return new ResponseEntity<>(creado, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar Gerente de Curso", description = "Permite actualizar la información de un gerente de curso existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Gerente actualizado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Gerente no encontrado")
    })
    public ResponseEntity<String> updateGerenteDeCursos(
            @Parameter(description = "ID del gerente de curso a actualizar", example = "1")
            @PathVariable int id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Datos actualizados del gerente de curso",
                    required = true,
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Gerente.class)))
            @RequestBody Gerente gerenteDeCursos) {
        String result = gerenteService.updateGerente(id, gerenteDeCursos);
        if (result.trim().startsWith("No se encuentra")) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(result, HttpStatus.OK);
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar Gerente de Curso por ID", description = "Elimina un gerente de curso específico usando su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Gerente eliminado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Gerente no encontrado")
    })
    public ResponseEntity<String> deleteGerenteDeCursos(
            @Parameter(description = "ID del gerente de curso a eliminar", example = "1")
            @PathVariable int id) {
        String result = gerenteService.deleteGerente(id);
        if (result.equals("No se encuentra")) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(result, HttpStatus.OK);
        }
    }
}

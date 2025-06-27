package com.API.API.controller;

import com.API.API.model.Curso;
import com.API.API.model.Evaluacion;
import com.API.API.model.Soporte;
import com.API.API.service.SoporteService;
import io.swagger.v3.oas.annotations.*;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;  // IMPORT normal, sin alias
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/Soportes")
@Tag(name = "Controlador Soportes", description = "API para gestión de soportes en el sistema")
public class SoporteController {

    @Autowired
    SoporteService soporteService;

    @GetMapping
    @Operation(summary = "Listar todos los soportes", description = "Obtiene la lista completa de soportes registrados")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de soportes obtenida",
                    content = @Content(mediaType = "application/json",  array = @ArraySchema(schema = @Schema(implementation = Soporte.class)))),
            @ApiResponse(responseCode = "404", description = "No se encontraron soportes", content = @Content),
    })
    public ResponseEntity<List<Soporte>> listar() {
        List<Soporte> lista = soporteService.getAllSoportes();
        if (lista.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(lista, HttpStatus.OK);
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener soporte por ID", description = "Obtiene un soporte específico mediante su ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Soporte encontrado",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Soporte.class))),
            @ApiResponse(responseCode = "404", description = "Soporte no encontrado",content = @Content)
    })
    public ResponseEntity<Soporte> getSoporte(
            @Parameter(description = "ID del soporte", example = "1")
            @PathVariable int id) {
        Soporte resultado = soporteService.getSoporte(id);
        if (resultado == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(resultado, HttpStatus.OK);
    }

    @PostMapping
    @Operation(summary = "Crear nuevo soporte", description = "Registra un nuevo soporte en el sistema")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Soporte creado exitosamente",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Soporte.class))),
            @ApiResponse(responseCode = "400", description = "Datos inválidos en la solicitud",content = @Content)
    })
    public ResponseEntity<Soporte> addSoporte(
            @RequestBody(description = "Datos del soporte a crear", required = true,
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Soporte.class)))
            @org.springframework.web.bind.annotation.RequestBody Soporte soporte) {
        Soporte creado = soporteService.addSoporte(soporte);
        return new ResponseEntity<>(creado, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar soporte", description = "Actualiza la información de un soporte existente")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Soporte actualizado correctamente"),
            @ApiResponse(responseCode = "404", description = "Soporte no encontrado")
    })
    public ResponseEntity<String> updateSoporte(
            @Parameter(description = "ID del soporte a actualizar", example = "1")
            @PathVariable int id,
            @RequestBody(description = "Datos actualizados del soporte", required = true,
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Soporte.class)))
            @org.springframework.web.bind.annotation.RequestBody Soporte soporte) {
        String resultado = soporteService.updateSoporte(id, soporte);
        if (resultado.trim().startsWith("No se encuentra")) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(resultado, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar soporte por ID", description = "Elimina un soporte específico usando su ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Soporte eliminado correctamente"),
            @ApiResponse(responseCode = "404", description = "Soporte no encontrado")
    })
    public ResponseEntity<String> deleteSoporte(
            @Parameter(description = "ID del soporte a eliminar", example = "1")
            @PathVariable int id) {
        String resultado = soporteService.deleteSoporte(id);
        if ("No se encuentra".equals(resultado)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(resultado, HttpStatus.OK);
    }
}

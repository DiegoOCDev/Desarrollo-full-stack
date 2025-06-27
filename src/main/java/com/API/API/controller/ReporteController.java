package com.API.API.controller;

import com.API.API.model.Curso;
import com.API.API.model.Evaluacion;
import com.API.API.model.Reporte;
import com.API.API.service.ReporteService;
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
@RequestMapping("/Reportes")
@Tag(name = "Controlador Reportes", description = "API para la gestión de reportes en el sistema")
public class ReporteController {

    @Autowired
    private ReporteService reporteService;

    @GetMapping
    @Operation(summary = "Obtener todos los Reportes", description = "Devuelve la lista completa de reportes registrados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de reportes obtenida",
                    content = @Content(mediaType = "application/json",  array = @ArraySchema(schema = @Schema(implementation = Reporte.class)))),
            @ApiResponse(responseCode = "404", description = "No se encontraron reportes", content = @Content),
    })
    public ResponseEntity<List<Reporte>> getReportes() {
        List<Reporte> reportes = reporteService.getAllReportes();
        if (reportes.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(reportes, HttpStatus.OK);
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener Reporte por ID", description = "Devuelve los detalles de un reporte específico por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Reporte encontrado",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Reporte.class))),
            @ApiResponse(responseCode = "404", description = "Reporte no encontrado", content = @Content)

    })
    public ResponseEntity<Reporte> getReporteById(
            @Parameter(description = "ID del reporte a buscar", example = "1")
            @PathVariable int id) {
        Reporte result = reporteService.getReporte(id);
        if (result == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(result, HttpStatus.OK);
        }
    }

    @PostMapping
    @Operation(summary = "Agregar Reporte", description = "Permite registrar un nuevo reporte en el sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Reporte creado exitosamente",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Reporte.class))),
            @ApiResponse(responseCode = "400", description = "Datos inválidos", content = @Content)
    })
    public ResponseEntity<Reporte> addReporte(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Datos del reporte a crear",
                    required = true,
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Reporte.class)))
            @RequestBody Reporte reporte) {
        Reporte creado = reporteService.addReporte(reporte);
        return new ResponseEntity<>(creado, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar Reporte", description = "Permite actualizar la información de un reporte existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Reporte actualizado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Reporte no encontrado")
    })
    public ResponseEntity<String> updateReporte(
            @Parameter(description = "ID del reporte a actualizar", example = "1")
            @PathVariable int id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Datos actualizados del reporte",
                    required = true,
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Reporte.class)))
            @RequestBody Reporte reporte) {
        String result = reporteService.updateReporte(id, reporte);
        if (result.trim().startsWith("No se encuentra")) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(result, HttpStatus.OK);
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar Reporte por ID", description = "Elimina un reporte específico usando su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Reporte eliminado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Reporte no encontrado")
    })
    public ResponseEntity<String> deleteReporte(
            @Parameter(description = "ID del reporte a eliminar", example = "1")
            @PathVariable int id) {
        String result = reporteService.deleteReporte(id);
        if (result.equals("No se encuentra")) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(result, HttpStatus.OK);
        }
    }
}

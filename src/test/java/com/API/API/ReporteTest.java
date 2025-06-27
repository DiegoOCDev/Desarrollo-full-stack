package com.API.API;

import com.API.API.model.Reporte;
import com.API.API.model.Soporte;
import com.API.API.model.Usuario;
import com.API.API.repository.ReporteRepository;
import com.API.API.service.ReporteService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc

public class ReporteTest {

    @MockitoBean
    private ReporteRepository reporteRepository;

    @Autowired
    private ReporteService reporteService;

    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

    private Reporte crearReporteDummy() {
        Reporte r = new Reporte();
        r.setTipoReporte("Error");
        r.setDatosReporte("Pantalla en blanco al ingresar.");
        r.setFechaReporte(LocalDate.now());
        r.setDescripcionReporte("El sistema no carga correctamente.");
        r.setIdSoporte(new Soporte());
        r.setIdReportante(new Usuario());
        return r;
    }

    // ==== Pruebas de Servicio ====

    @Test
    @DisplayName("Agregar reporte - Servicio")
    void testAddReporte() {
        Reporte reporte = crearReporteDummy();

        when(reporteRepository.save(reporte)).thenReturn(reporte);

        Reporte resultado = reporteService.addReporte(reporte);

        verify(reporteRepository).save(reporte);
        assertEquals(reporte, resultado);
    }

    @Test
    @DisplayName("Eliminar reporte existente - Servicio")
    void testDeleteReporteExistente() {
        when(reporteRepository.existsById(1)).thenReturn(true);

        String resultado = reporteService.deleteReporte(1);

        verify(reporteRepository).deleteById(1);
        assertEquals("eliminado con exito", resultado);
    }

    @Test
    @DisplayName("Eliminar reporte inexistente - Servicio")
    void testDeleteReporteInexistente() {
        when(reporteRepository.existsById(1)).thenReturn(false);

        String resultado = reporteService.deleteReporte(1);

        verify(reporteRepository, never()).deleteById(anyInt());
        assertEquals("No se encuentra", resultado);
    }

    @Test
    @DisplayName("Actualizar reporte existente - Servicio")
    void testUpdateReporteExistente() {
        Reporte viejo = crearReporteDummy();
        viejo.setDatosReporte("Antiguo");

        Reporte nuevo = crearReporteDummy();
        nuevo.setDatosReporte("Actualizado");
        nuevo.setDescripcionReporte("Descripción nueva");
        nuevo.setFechaReporte(LocalDate.of(2025, 1, 1));
        nuevo.setTipoReporte("Actualización");

        when(reporteRepository.existsById(1)).thenReturn(true);
        when(reporteRepository.findById(1)).thenReturn(Optional.of(viejo));

        String resultado = reporteService.updateReporte(1, nuevo);

        verify(reporteRepository).save(viejo);
        assertEquals(" actualizado con exito", resultado);
        assertEquals("Actualizado", viejo.getDatosReporte());
        assertEquals("Actualización", viejo.getTipoReporte());
    }

    @Test
    @DisplayName("Actualizar reporte inexistente - Servicio")
    void testUpdateReporteInexistente() {
        Reporte nuevo = crearReporteDummy();

        when(reporteRepository.existsById(1)).thenReturn(false);

        String resultado = reporteService.updateReporte(1, nuevo);

        verify(reporteRepository, never()).save(any());
        assertEquals("No se encuentra ", resultado);
    }

    @Test
    @DisplayName("Obtener reporte existente - Servicio")
    void testGetReporteExistente() {
        Reporte reporte = crearReporteDummy();

        when(reporteRepository.existsById(1)).thenReturn(true);
        when(reporteRepository.findById(1)).thenReturn(Optional.of(reporte));

        Reporte resultado = reporteService.getReporte(1);

        assertEquals(reporte, resultado);
    }

    @Test
    @DisplayName("Obtener reporte inexistente - Servicio")
    void testGetReporteInexistente() {
        when(reporteRepository.existsById(1)).thenReturn(false);

        Reporte resultado = reporteService.getReporte(1);

        assertNull(resultado);
    }

    @Test
    @DisplayName("Obtener todos los reportes - Servicio")
    void testGetAllReportes() {
        Reporte r1 = crearReporteDummy();
        Reporte r2 = crearReporteDummy();
        r2.setTipoReporte("Sugerencia");

        List<Reporte> lista = Arrays.asList(r1, r2);

        when(reporteRepository.findAll()).thenReturn(lista);

        List<Reporte> resultado = reporteService.getAllReportes();

        assertEquals(2, resultado.size());
        assertEquals("Error", resultado.get(0).getTipoReporte());
        assertEquals("Sugerencia", resultado.get(1).getTipoReporte());
    }

    // ==== Pruebas Controlador ====

    @Test
    @DisplayName("GET /Reportes - Obtener todos los reportes")
    void testGetAllReportesController() throws Exception {
        List<Reporte> lista = Arrays.asList(crearReporteDummy(), crearReporteDummy());
        when(reporteRepository.findAll()).thenReturn(lista);

        mockMvc.perform(get("/Reportes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(lista.size()));
    }

    @Test
    @DisplayName("GET /Reportes/{id} - Reporte existente")
    void testGetReportePorIdControllerExistente() throws Exception {
        Reporte reporte = crearReporteDummy();
        when(reporteRepository.existsById(1)).thenReturn(true);
        when(reporteRepository.findById(1)).thenReturn(Optional.of(reporte));

        mockMvc.perform(get("/Reportes/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.tipoReporte").value("Error"));
    }

    @Test
    @DisplayName("POST /Reportes - Crear reporte")
    void testAddReporteController() throws Exception {
        Reporte reporte = crearReporteDummy();
        when(reporteRepository.save(any())).thenReturn(reporte);

        mockMvc.perform(post("/Reportes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(reporte)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.tipoReporte").value("Error"));
    }

    @Test
    @DisplayName("PUT /Reportes/{id} - Actualizar reporte")
    void testUpdateReporteController() throws Exception {
        Reporte viejo = crearReporteDummy();
        Reporte nuevo = crearReporteDummy();
        nuevo.setTipoReporte("Actualizado");

        when(reporteRepository.existsById(1)).thenReturn(true);
        when(reporteRepository.findById(1)).thenReturn(Optional.of(viejo));
        when(reporteRepository.save(any())).thenReturn(viejo);

        mockMvc.perform(put("/Reportes/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(nuevo)))
                .andExpect(status().isOk())
                .andExpect(content().string(" actualizado con exito"));
    }
}

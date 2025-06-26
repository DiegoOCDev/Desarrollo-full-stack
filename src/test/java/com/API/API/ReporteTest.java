package com.API.API;

import com.API.API.model.Reporte;
import com.API.API.model.Soporte;
import com.API.API.model.Usuario;
import com.API.API.repository.ReporteRepository;
import com.API.API.service.ReporteService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class ReporteTest {

    @MockitoBean
    private ReporteRepository reporteRepository;

    @MockitoBean
    private ReporteService reporteService;

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

    @Test
    void testAddReporte() {
        Reporte reporte = crearReporteDummy();

        when(reporteRepository.save(reporte)).thenReturn(reporte);

        Reporte resultado = reporteService.addReporte(reporte);

        verify(reporteRepository).save(reporte);
        assertEquals(reporte, resultado);
    }

    @Test
    void testDeleteReporteExistente() {
        when(reporteRepository.existsById(1)).thenReturn(true);

        String resultado = reporteService.deleteReporte(1);

        verify(reporteRepository).deleteById(1);
        assertEquals("eliminado con exito", resultado);
    }

    @Test
    void testDeleteReporteInexistente() {
        when(reporteRepository.existsById(1)).thenReturn(false);

        String resultado = reporteService.deleteReporte(1);

        verify(reporteRepository, never()).deleteById(anyInt());
        assertEquals("No se encuentra", resultado);
    }

    @Test
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
    void testUpdateReporteInexistente() {
        Reporte nuevo = crearReporteDummy();

        when(reporteRepository.existsById(1)).thenReturn(false);

        String resultado = reporteService.updateReporte(1, nuevo);

        verify(reporteRepository, never()).save(any());
        assertEquals("No se encuentra ", resultado);
    }

    @Test
    void testGetReporteExistente() {
        Reporte reporte = crearReporteDummy();

        when(reporteRepository.existsById(1)).thenReturn(true);
        when(reporteRepository.findById(1)).thenReturn(Optional.of(reporte));

        String resultado = reporteService.getReporte(1);

        assertEquals(reporte.toString(), resultado);
    }

    @Test
    void testGetReporteInexistente() {
        when(reporteRepository.existsById(1)).thenReturn(false);

        String resultado = reporteService.getReporte(1);

        assertEquals("No se encuentra", resultado);
    }

    @Test
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
}

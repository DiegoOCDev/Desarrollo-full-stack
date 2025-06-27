package com.API.API;

import com.API.API.model.Soporte;
import com.API.API.repository.SoporteRepository;
import com.API.API.service.SoporteService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc

public class SoporteTest {

    @MockitoBean
    private SoporteRepository soporteRepository;

    @Autowired
    private SoporteService soporteService;

    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private Soporte crearSoporteDummy() {
        Soporte soporte = new Soporte();
        soporte.setNombreSoporte("Pedro Soporte");
        soporte.setCorreoSoporte("pedro@soporte.cl");
        return soporte;
    }

    // ==== Pruebas de Servicio ====

    @Test
    @DisplayName("Agregar soporte - Servicio")
    void testAddSoporte() {
        Soporte soporte = crearSoporteDummy();

        when(soporteRepository.save(soporte)).thenReturn(soporte);

        Soporte resultado = soporteService.addSoporte(soporte);

        verify(soporteRepository).save(soporte);
        assertEquals(soporte, resultado);
    }

    @Test
    @DisplayName("Eliminar soporte existente - Servicio")
    void testDeleteSoporteExistente() {
        when(soporteRepository.existsById(1)).thenReturn(true);

        String resultado = soporteService.deleteSoporte(1);

        verify(soporteRepository).deleteById(1);
        assertEquals("eliminado con exito", resultado);
    }

    @Test
    @DisplayName("Eliminar soporte inexistente - Servicio")
    void testDeleteSoporteInexistente() {
        when(soporteRepository.existsById(1)).thenReturn(false);

        String resultado = soporteService.deleteSoporte(1);

        verify(soporteRepository, never()).deleteById(anyInt());
        assertEquals("No se encuentra", resultado);
    }

    @Test
    @DisplayName("Actualizar soporte existente - Servicio")
    void testUpdateSoporteExistente() {
        Soporte viejo = crearSoporteDummy();
        viejo.setNombreSoporte("Antiguo");

        Soporte nuevo = crearSoporteDummy();
        nuevo.setNombreSoporte("Actualizado");
        nuevo.setCorreoSoporte("nuevo@soporte.cl");

        when(soporteRepository.existsById(1)).thenReturn(true);
        when(soporteRepository.findById(1)).thenReturn(Optional.of(viejo));

        String resultado = soporteService.updateSoporte(1, nuevo);

        verify(soporteRepository).save(viejo);
        assertEquals(" actualizado con exito", resultado);
        assertEquals("Actualizado", viejo.getNombreSoporte());
        assertEquals("nuevo@soporte.cl", viejo.getCorreoSoporte());
    }

    @Test
    @DisplayName("Actualizar soporte inexistente - Servicio")
    void testUpdateSoporteInexistente() {
        Soporte nuevo = crearSoporteDummy();

        when(soporteRepository.existsById(1)).thenReturn(false);

        String resultado = soporteService.updateSoporte(1, nuevo);

        verify(soporteRepository, never()).save(any());
        assertEquals("No se encuentra ", resultado);
    }

    @Test
    @DisplayName("Obtener soporte existente - Servicio")
    void testGetSoporteExistente() {
        Soporte soporte = crearSoporteDummy();

        when(soporteRepository.existsById(1)).thenReturn(true);
        when(soporteRepository.findById(1)).thenReturn(Optional.of(soporte));

        Soporte resultado = soporteService.getSoporte(1);

        assertEquals(soporte, resultado);
    }

    @Test
    @DisplayName("Obtener soporte inexistente - Servicio")
    void testGetSoporteInexistente() {
        when(soporteRepository.existsById(1)).thenReturn(false);

        Soporte resultado = soporteService.getSoporte(1);

        assertNull(resultado);
    }

    @Test
    @DisplayName("Obtener todos los soportes - Servicio")
    void testGetAllSoportes() {
        Soporte s1 = crearSoporteDummy();
        Soporte s2 = crearSoporteDummy();
        s2.setNombreSoporte("Sofía Ayuda");

        List<Soporte> lista = Arrays.asList(s1, s2);

        when(soporteRepository.findAll()).thenReturn(lista);

        List<Soporte> resultado = soporteService.getAllSoportes();

        assertEquals(2, resultado.size());
        assertEquals("Pedro Soporte", resultado.get(0).getNombreSoporte());
        assertEquals("Sofía Ayuda", resultado.get(1).getNombreSoporte());
    }

    // ==== Pruebas Controlador ====

    @Test
    @DisplayName("GET /Soportes - Obtener todos los soportes")
    void testGetAllSoportesController() throws Exception {
        List<Soporte> lista = Arrays.asList(crearSoporteDummy(), crearSoporteDummy());
        when(soporteRepository.findAll()).thenReturn(lista);

        mockMvc.perform(get("/Soportes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(lista.size()));
    }

    @Test
    @DisplayName("GET /Soportes/{id} - Soporte existente")
    void testGetSoportePorIdControllerExistente() throws Exception {
        Soporte soporte = crearSoporteDummy();
        when(soporteRepository.existsById(1)).thenReturn(true);
        when(soporteRepository.findById(1)).thenReturn(Optional.of(soporte));

        mockMvc.perform(get("/Soportes/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombreSoporte").value("Pedro Soporte"));
    }

    @Test
    @DisplayName("POST /Soportes - Crear soporte")
    void testAddSoporteController() throws Exception {
        Soporte soporte = crearSoporteDummy();
        when(soporteRepository.save(any())).thenReturn(soporte);

        mockMvc.perform(post("/Soportes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(soporte)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nombreSoporte").value("Pedro Soporte"));
    }

    @Test
    @DisplayName("PUT /Soportes/{id} - Actualizar soporte")
    void testUpdateSoporteController() throws Exception {
        Soporte viejo = crearSoporteDummy();
        Soporte nuevo = crearSoporteDummy();
        nuevo.setNombreSoporte("Actualizado");

        when(soporteRepository.existsById(1)).thenReturn(true);
        when(soporteRepository.findById(1)).thenReturn(Optional.of(viejo));
        when(soporteRepository.save(any())).thenReturn(viejo);

        mockMvc.perform(put("/Soportes/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(nuevo)))
                .andExpect(status().isOk())
                .andExpect(content().string(" actualizado con exito"));
    }
}

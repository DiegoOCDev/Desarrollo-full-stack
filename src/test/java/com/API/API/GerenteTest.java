package com.API.API;

import com.API.API.model.Gerente;
import com.API.API.repository.GerenteRepository;
import com.API.API.service.GerenteService;
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
public class GerenteTest {

    @MockitoBean
    private GerenteRepository gerenteRepository;

    @Autowired
    private GerenteService gerenteService;

    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private Gerente crearGerenteDummy() {
        Gerente g = new Gerente();
        g.setId(1);
        g.setNombreGerente("María González");
        g.setCorreoGerente("maria@empresa.com");
        return g;
    }

    // ------------------- Servicio -------------------

    @Test
    @DisplayName("Servicio - Agregar gerente")
    void testAddGerente() {
        Gerente g = crearGerenteDummy();
        when(gerenteRepository.save(g)).thenReturn(g);

        Gerente result = gerenteService.addGerente(g);

        verify(gerenteRepository).save(g);
        assertEquals("María González", result.getNombreGerente());
    }

    @Test
    @DisplayName("Servicio - Obtener gerente por ID existente")
    void testGetGerenteExistente() {
        Gerente g = crearGerenteDummy();
        when(gerenteRepository.existsById(1)).thenReturn(true);
        when(gerenteRepository.findById(1)).thenReturn(Optional.of(g));

        Gerente result = gerenteService.getGerente(1);

        assertNotNull(result);
        assertEquals("María González", result.getNombreGerente());
    }

    @Test
    @DisplayName("Servicio - Eliminar gerente existente")
    void testDeleteGerente() {
        when(gerenteRepository.existsById(1)).thenReturn(true);

        String msg = gerenteService.deleteGerente(1);

        verify(gerenteRepository).deleteById(1);
        assertEquals("eliminado con exito", msg);
    }

    @Test
    @DisplayName("Servicio - Actualizar gerente existente")
    void testUpdateGerente() {
        Gerente viejo = crearGerenteDummy();
        Gerente nuevo = crearGerenteDummy();
        nuevo.setNombreGerente("Nuevo Nombre");
        nuevo.setCorreoGerente("nuevo@correo.com");

        when(gerenteRepository.existsById(1)).thenReturn(true);
        when(gerenteRepository.findById(1)).thenReturn(Optional.of(viejo));

        String msg = gerenteService.updateGerente(1, nuevo);

        verify(gerenteRepository).save(viejo);
        assertEquals(" actualizado con exito", msg);
        assertEquals("Nuevo Nombre", viejo.getNombreGerente());
    }

    // ------------------- Controlador -------------------

    @Test
    @DisplayName("Controlador - GET /GerenteDeCursos")
    void testGetAllGerentesController() throws Exception {
        List<Gerente> lista = Arrays.asList(crearGerenteDummy(), crearGerenteDummy());
        when(gerenteRepository.findAll()).thenReturn(lista);

        mockMvc.perform(get("/GerenteDeCursos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].nombreGerente").value("María González"));
    }

    @Test
    @DisplayName("Controlador - GET /GerenteDeCursos/{id}")
    void testGetGerenteByIdController() throws Exception {
        Gerente g = crearGerenteDummy();
        when(gerenteRepository.existsById(1)).thenReturn(true);
        when(gerenteRepository.findById(1)).thenReturn(Optional.of(g));

        mockMvc.perform(get("/GerenteDeCursos/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombreGerente").value("María González"));
    }

    @Test
    @DisplayName("Controlador - POST /GerenteDeCursos")
    void testAddGerenteController() throws Exception {
        Gerente g = crearGerenteDummy();
        when(gerenteRepository.save(any(Gerente.class))).thenReturn(g);

        mockMvc.perform(post("/GerenteDeCursos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(g)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.correoGerente").value("maria@empresa.com"));
    }

    @Test
    @DisplayName("Controlador - DELETE /GerenteDeCursos/{id}")
    void testDeleteGerenteController() throws Exception {
        when(gerenteRepository.existsById(1)).thenReturn(true);

        mockMvc.perform(delete("/GerenteDeCursos/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("eliminado con exito"));
    }
}

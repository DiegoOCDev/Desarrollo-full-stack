package com.API.API;

import com.API.API.model.Curso;
import com.API.API.model.Estudiante;
import com.API.API.model.Evaluacion;
import com.API.API.model.Instructor;
import com.API.API.repository.EvaluacionRepository;
import com.API.API.service.EvaluacionService;
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

public class EvaluacionTest {

    @MockitoBean
    private EvaluacionRepository evaluacionRepository;

    @Autowired
    private EvaluacionService evaluacionService;

    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private Evaluacion crearEvaluacionDummy() {
        Evaluacion eval = new Evaluacion();
        eval.setNotaEvaluacion(85);
        eval.setIdCurso(new Curso());
        eval.setIdEstudiante(new Estudiante());
        eval.setIdInstructor(new Instructor());
        return eval;
    }

    // ===== Pruebas Servicio =====

    @Test
    @DisplayName("Agregar Evaluacion - Servicio")
    void testAddEvaluacion() {
        Evaluacion evaluacion = crearEvaluacionDummy();
        when(evaluacionRepository.save(evaluacion)).thenReturn(evaluacion);

        Evaluacion resultado = evaluacionService.addEvaluacion(evaluacion);

        verify(evaluacionRepository).save(evaluacion);
        assertEquals(evaluacion, resultado);
    }

    @Test
    @DisplayName("Eliminar Evaluacion existente - Servicio")
    void testDeleteEvaluacionExistente() {
        when(evaluacionRepository.existsById(1)).thenReturn(true);

        String resultado = evaluacionService.deleteEvaluacion(1);

        verify(evaluacionRepository).deleteById(1);
        assertEquals("eliminado con exito", resultado);
    }

    @Test
    @DisplayName("Eliminar Evaluacion inexistente - Servicio")
    void testDeleteEvaluacionInexistente() {
        when(evaluacionRepository.existsById(1)).thenReturn(false);

        String resultado = evaluacionService.deleteEvaluacion(1);

        verify(evaluacionRepository, never()).deleteById(anyInt());
        assertEquals("No se encuentra", resultado);
    }

    @Test
    @DisplayName("Actualizar Evaluacion existente - Servicio")
    void testUpdateEvaluacionExistente() {
        Evaluacion viejo = crearEvaluacionDummy();
        viejo.setNotaEvaluacion(60);
        Evaluacion nuevo = crearEvaluacionDummy();
        nuevo.setNotaEvaluacion(95);

        when(evaluacionRepository.existsById(1)).thenReturn(true);
        when(evaluacionRepository.findById(1)).thenReturn(Optional.of(viejo));

        String resultado = evaluacionService.updateEvaluacion(1, nuevo);

        verify(evaluacionRepository).save(viejo);
        assertEquals(" actualizado con exito", resultado);
        assertEquals(95, viejo.getNotaEvaluacion());
    }

    @Test
    @DisplayName("Actualizar Evaluacion inexistente - Servicio")
    void testUpdateEvaluacionInexistente() {
        Evaluacion nuevo = crearEvaluacionDummy();
        nuevo.setNotaEvaluacion(100);

        when(evaluacionRepository.existsById(1)).thenReturn(false);

        String resultado = evaluacionService.updateEvaluacion(1, nuevo);

        verify(evaluacionRepository, never()).save(any());
        assertEquals("No se encuentra ", resultado);
    }

    @Test
    @DisplayName("Obtener Evaluacion existente - Servicio")
    void testGetEvaluacionExistente() {
        Evaluacion evaluacion = crearEvaluacionDummy();

        when(evaluacionRepository.existsById(1)).thenReturn(true);
        when(evaluacionRepository.findById(1)).thenReturn(Optional.of(evaluacion));

        Evaluacion resultado = evaluacionService.getEvaluacion(1);

        assertEquals(evaluacion, resultado);
    }

    @Test
    @DisplayName("Obtener Evaluacion inexistente - Servicio")
    void testGetEvaluacionInexistente() {
        when(evaluacionRepository.existsById(1)).thenReturn(false);

        Evaluacion resultado = evaluacionService.getEvaluacion(1);

        assertNull(resultado);
    }

    @Test
    @DisplayName("Obtener todas las Evaluaciones - Servicio")
    void testGetAllEvaluaciones() {
        Evaluacion e1 = crearEvaluacionDummy();
        e1.setNotaEvaluacion(70);
        Evaluacion e2 = crearEvaluacionDummy();
        e2.setNotaEvaluacion(90);

        List<Evaluacion> lista = Arrays.asList(e1, e2);

        when(evaluacionRepository.findAll()).thenReturn(lista);

        List<Evaluacion> resultado = evaluacionService.getAllEvaluaciones();

        assertEquals(2, resultado.size());
        assertEquals(70, resultado.get(0).getNotaEvaluacion());
        assertEquals(90, resultado.get(1).getNotaEvaluacion());
    }

    // ===== Pruebas Controlador =====

    @Test
    @DisplayName("GET /Evaluacions - Obtener todas las evaluaciones")
    void testGetAllEvaluacionesController() throws Exception {
        List<Evaluacion> lista = Arrays.asList(crearEvaluacionDummy(), crearEvaluacionDummy());
        when(evaluacionRepository.findAll()).thenReturn(lista);

        mockMvc.perform(get("/Evaluacions"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(lista.size()));
    }

    @Test
    @DisplayName("GET /Evaluacions/{id} - Evaluacion existente")
    void testGetEvaluacionPorIdControllerExistente() throws Exception {
        Evaluacion eval = crearEvaluacionDummy();
        when(evaluacionRepository.existsById(1)).thenReturn(true);
        when(evaluacionRepository.findById(1)).thenReturn(Optional.of(eval));

        mockMvc.perform(get("/Evaluacions/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.notaEvaluacion").value(85));
    }

    @Test
    @DisplayName("POST /Evaluacions - Crear evaluación")
    void testAddEvaluacionController() throws Exception {
        Evaluacion eval = crearEvaluacionDummy();
        when(evaluacionRepository.save(any())).thenReturn(eval);

        mockMvc.perform(post("/Evaluacions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(eval)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.notaEvaluacion").value(85));
    }

    @Test
    @DisplayName("PUT /Evaluacions/{id} - Actualizar evaluación")
    void testUpdateEvaluacionController() throws Exception {
        Evaluacion viejo = crearEvaluacionDummy();
        Evaluacion nuevo = crearEvaluacionDummy();
        nuevo.setNotaEvaluacion(99);

        when(evaluacionRepository.existsById(1)).thenReturn(true);
        when(evaluacionRepository.findById(1)).thenReturn(Optional.of(viejo));
        when(evaluacionRepository.save(any())).thenReturn(viejo);

        mockMvc.perform(put("/Evaluacions/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(nuevo)))
                .andExpect(status().isOk())
                .andExpect(content().string(" actualizado con exito"));
    }
}

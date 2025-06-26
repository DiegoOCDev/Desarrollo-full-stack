package com.API.API;

import com.API.API.model.Curso;
import com.API.API.model.Estudiante;
import com.API.API.model.Evaluacion;
import com.API.API.model.Instructor;
import com.API.API.repository.EvaluacionRepository;
import com.API.API.service.EvaluacionService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class EvaluacionTest {

    @MockitoBean
    private EvaluacionRepository evaluacionRepository;

    @MockitoBean
    private EvaluacionService evaluacionService;

    private Evaluacion crearEvaluacionDummy() {
        Evaluacion eval = new Evaluacion();
        eval.setNotaEvaluacion(85);
        eval.setIdCurso(new Curso());
        eval.setIdEstudiante(new Estudiante());
        eval.setIdInstructor(new Instructor());
        return eval;
    }

    @Test
    void testAddEvaluacion() {
        Evaluacion evaluacion = crearEvaluacionDummy();

        when(evaluacionRepository.save(evaluacion)).thenReturn(evaluacion);

        Evaluacion resultado = evaluacionService.addEvaluacion(evaluacion);

        verify(evaluacionRepository).save(evaluacion);
        assertEquals(evaluacion, resultado);
    }

    @Test
    void testDeleteEvaluacionExistente() {
        when(evaluacionRepository.existsById(1)).thenReturn(true);

        String resultado = evaluacionService.deleteEvaluacion(1);

        verify(evaluacionRepository).deleteById(1);
        assertEquals("eliminado con exito", resultado);
    }

    @Test
    void testDeleteEvaluacionInexistente() {
        when(evaluacionRepository.existsById(1)).thenReturn(false);

        String resultado = evaluacionService.deleteEvaluacion(1);

        verify(evaluacionRepository, never()).deleteById(anyInt());
        assertEquals("No se encuentra", resultado);
    }

    @Test
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
    void testUpdateEvaluacionInexistente() {
        Evaluacion nuevo = crearEvaluacionDummy();
        nuevo.setNotaEvaluacion(100);

        when(evaluacionRepository.existsById(1)).thenReturn(false);

        String resultado = evaluacionService.updateEvaluacion(1, nuevo);

        verify(evaluacionRepository, never()).save(any());
        assertEquals("No se encuentra ", resultado);
    }

    @Test
    void testGetEvaluacionExistente() {
        Evaluacion evaluacion = crearEvaluacionDummy();

        when(evaluacionRepository.existsById(1)).thenReturn(true);
        when(evaluacionRepository.findById(1)).thenReturn(Optional.of(evaluacion));

        String resultado = evaluacionService.getEvaluacion(1);

        assertEquals(evaluacion.toString(), resultado);
    }

    @Test
    void testGetEvaluacionInexistente() {
        when(evaluacionRepository.existsById(1)).thenReturn(false);

        String resultado = evaluacionService.getEvaluacion(1);

        assertEquals("No se encuentra", resultado);
    }

    @Test
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
}

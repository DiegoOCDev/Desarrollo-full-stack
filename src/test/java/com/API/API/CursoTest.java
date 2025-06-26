
package com.API.API;

import com.API.API.model.Curso;
import com.API.API.model.Gerente;
import com.API.API.repository.CursoRepository;
import com.API.API.service.CursoService;
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
public class CursoTest {

    @MockitoBean
    private CursoRepository cursoRepository;

    @MockitoBean
    private CursoService cursoService;

    @Test
    void testAddCurso() {
        Curso curso = new Curso();
        curso.setTituloCurso("Java Básico");
        curso.setDescripcionCurso("Curso de Java para principiantes");
        curso.setEstadoCurso("Activo");
        curso.setFechaInicio(LocalDate.of(2024, 1, 1));
        curso.setFechaFin(LocalDate.of(2024, 6, 30));
        curso.setIdGerente(new Gerente()); // puedes poner un gerente dummy si quieres

        when(cursoRepository.save(curso)).thenReturn(curso);

        Curso resultado = cursoService.addCurso(curso);

        verify(cursoRepository).save(curso);
        assertEquals(curso, resultado);
    }

    @Test
    void testDeleteCursoExistente() {
        when(cursoRepository.existsById(1)).thenReturn(true);

        String resultado = cursoService.deleteCurso(1);

        verify(cursoRepository).deleteById(1);
        assertEquals("eliminado con exito", resultado);
    }

    @Test
    void testDeleteCursoInexistente() {
        when(cursoRepository.existsById(99)).thenReturn(false);

        String resultado = cursoService.deleteCurso(99);

        verify(cursoRepository, never()).deleteById(anyInt());
        assertEquals("No se encuentra", resultado);
    }

    @Test
    void testUpdateCursoExistente() {
        Curso viejo = new Curso();
        viejo.setTituloCurso("Java Antiguo");
        viejo.setDescripcionCurso("Viejo");
        viejo.setEstadoCurso("Inactivo");
        viejo.setFechaInicio(LocalDate.of(2023,1,1));
        viejo.setFechaFin(LocalDate.of(2023,12,31));

        Curso nuevo = new Curso();
        nuevo.setTituloCurso("Java Nuevo");
        nuevo.setDescripcionCurso("Actualizado");
        nuevo.setEstadoCurso("Activo");
        nuevo.setFechaInicio(LocalDate.of(2024,1,1));
        nuevo.setFechaFin(LocalDate.of(2024,12,31));

        when(cursoRepository.existsById(1)).thenReturn(true);
        when(cursoRepository.findById(1)).thenReturn(Optional.of(viejo));

        String resultado = cursoService.updateCurso(1, nuevo);

        verify(cursoRepository).save(viejo);
        assertEquals(" actualizado con exito", resultado);
        assertEquals("Java Nuevo", viejo.getTituloCurso());
        assertEquals("Actualizado", viejo.getDescripcionCurso());
        assertEquals("Activo", viejo.getEstadoCurso());
        assertEquals(LocalDate.of(2024,1,1), viejo.getFechaInicio());
        assertEquals(LocalDate.of(2024,12,31), viejo.getFechaFin());
    }

    @Test
    void testUpdateCursoInexistente() {
        Curso nuevo = new Curso();
        nuevo.setTituloCurso("Java Nuevo");
        nuevo.setDescripcionCurso("Actualizado");
        nuevo.setEstadoCurso("Activo");

        when(cursoRepository.existsById(1)).thenReturn(false);

        String resultado = cursoService.updateCurso(1, nuevo);

        verify(cursoRepository, never()).save(any());
        assertEquals("No se encuentra ", resultado);
    }

    @Test
    void testGetCursoExistente() {
        Curso curso = new Curso();
        curso.setTituloCurso("Curso Existente");
        curso.setDescripcionCurso("Descripción");
        curso.setEstadoCurso("Activo");

        when(cursoRepository.existsById(1)).thenReturn(true);
        when(cursoRepository.findById(1)).thenReturn(Optional.of(curso));

        String resultado = cursoService.getCurso(1);

        assertEquals(curso.toString(), resultado);
    }

    @Test
    void testGetCursoInexistente() {
        when(cursoRepository.existsById(1)).thenReturn(false);

        String resultado = cursoService.getCurso(1);

        assertEquals("No se encuentra", resultado);
    }

    @Test
    void testGetAllCursos() {
        Curso c1 = new Curso();
        c1.setTituloCurso("Curso 1");
        Curso c2 = new Curso();
        c2.setTituloCurso("Curso 2");

        List<Curso> lista = Arrays.asList(c1, c2);

        when(cursoRepository.findAll()).thenReturn(lista);

        List<Curso> resultado = cursoService.getAllCursos();

        assertEquals(2, resultado.size());
        assertEquals("Curso 1", resultado.get(0).getTituloCurso());
    }
}

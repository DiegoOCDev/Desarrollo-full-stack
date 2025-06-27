package com.API.API;

import com.API.API.controller.CursoController;
import com.API.API.model.Curso;
import com.API.API.model.Gerente;
import com.API.API.repository.CursoRepository;
import com.API.API.service.CursoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

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
public class CursoTest {

    @MockitoBean
    private CursoRepository cursoRepository;

    @Autowired
    private CursoService cursoService;

    @Autowired
    private MockMvc mockMvc;


    private final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

    private Curso crearCursoDummy() {
        Curso curso = new Curso();
        curso.setTituloCurso("Curso de Java");
        curso.setDescripcionCurso("Aprende Java");
        curso.setEstadoCurso("Activo");
        curso.setFechaInicio(LocalDate.of(2024, 1, 1));
        curso.setFechaFin(LocalDate.of(2024, 6, 30));
        curso.setIdGerente(new Gerente());
        return curso;
    }

    @Test
    @DisplayName("Agregar curso")
    void testAddCurso() {
        Curso curso = crearCursoDummy();
        when(cursoRepository.save(curso)).thenReturn(curso);

        Curso resultado = cursoService.addCurso(curso);

        verify(cursoRepository).save(curso);
        assertEquals(curso, resultado);
    }

    @Test
    @DisplayName("Eliminar curso existente")
    void testDeleteCursoExistente() {
        when(cursoRepository.existsById(1)).thenReturn(true);

        String resultado = cursoService.deleteCurso(1);

        verify(cursoRepository).deleteById(1);
        assertEquals("eliminado con exito", resultado);
    }

    @Test
    @DisplayName("Eliminar curso inexistente")
    void testDeleteCursoInexistente() {
        when(cursoRepository.existsById(99)).thenReturn(false);

        String resultado = cursoService.deleteCurso(99);

        verify(cursoRepository, never()).deleteById(anyInt());
        assertEquals("No se encuentra", resultado);
    }

    @Test
    @DisplayName("Actualizar curso existente")
    void testUpdateCursoExistente() {
        Curso viejo = crearCursoDummy();
        Curso nuevo = crearCursoDummy();
        nuevo.setTituloCurso("Curso Avanzado");

        when(cursoRepository.existsById(1)).thenReturn(true);
        when(cursoRepository.findById(1)).thenReturn(Optional.of(viejo));

        String resultado = cursoService.updateCurso(1, nuevo);

        verify(cursoRepository).save(viejo);
        assertEquals(" actualizado con exito", resultado);
        assertEquals("Curso Avanzado", viejo.getTituloCurso());
    }

    @Test
    @DisplayName("Actualizar curso inexistente")
    void testUpdateCursoInexistente() {
        Curso nuevo = crearCursoDummy();
        when(cursoRepository.existsById(1)).thenReturn(false);

        String resultado = cursoService.updateCurso(1, nuevo);

        verify(cursoRepository, never()).save(any());
        assertEquals("No se encuentra ", resultado);
    }

    @Test
    @DisplayName("Obtener curso existente")
    void testGetCursoExistente() {
        Curso curso = crearCursoDummy();
        when(cursoRepository.existsById(1)).thenReturn(true);
        when(cursoRepository.findById(1)).thenReturn(Optional.of(curso));

        Curso resultado = cursoService.getCurso(1);

        assertEquals(curso, resultado);
    }

    @Test
    @DisplayName("Obtener curso inexistente")
    void testGetCursoInexistente() {
        when(cursoRepository.existsById(1)).thenReturn(false);

        Curso resultado = cursoService.getCurso(1);

        assertNull(resultado);
    }

    @Test
    @DisplayName("Obtener todos los cursos")
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

    @Test
    @DisplayName("GET /Cursos - obtener lista")
    void testGetAllCursosController() throws Exception {
        List<Curso> cursos = Arrays.asList(crearCursoDummy(), crearCursoDummy());
        when(cursoRepository.findAll()).thenReturn(cursos);

        mockMvc.perform(get("/Cursos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(cursos.size()));
    }

    @Test
    @DisplayName("GET /Cursos/{id} - curso encontrado")
    void testGetCursoPorIdControllerExistente() throws Exception {
        Curso curso = crearCursoDummy();
        when(cursoRepository.existsById(1)).thenReturn(true);
        when(cursoRepository.findById(1)).thenReturn(Optional.of(curso));

        mockMvc.perform(get("/Cursos/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.tituloCurso").value("Curso de Java"));
    }

    @Test
    @DisplayName("POST /Cursos - crear curso")
    void testAddCursoController() throws Exception {
        Curso curso = crearCursoDummy();
        when(cursoRepository.save(any())).thenReturn(curso);

        mockMvc.perform(post("/Cursos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(curso)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.tituloCurso").value("Curso de Java"));
    }

    @Test
    @DisplayName("PUT /Cursos/{id} - actualizar curso")
    void testUpdateCursoController() throws Exception {
        Curso viejo = crearCursoDummy();
        Curso nuevo = crearCursoDummy();
        nuevo.setTituloCurso("Curso Actualizado");

        when(cursoRepository.existsById(1)).thenReturn(true);
        when(cursoRepository.findById(1)).thenReturn(Optional.of(viejo));
        when(cursoRepository.save(any())).thenReturn(viejo);

        mockMvc.perform(put("/Cursos/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(nuevo)))
                .andExpect(status().isOk())
                .andExpect(content().string(" actualizado con exito"));
    }
}
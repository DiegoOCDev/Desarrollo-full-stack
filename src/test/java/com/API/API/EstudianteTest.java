package com.API.API;

import com.API.API.model.Estudiante;
import com.API.API.model.Usuario;
import com.API.API.repository.EstudianteRepository;
import com.API.API.service.EstudianteService;
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

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc

public class EstudianteTest {

    @MockitoBean
    private EstudianteRepository estudianteRepository;

    @Autowired
    private EstudianteService estudianteService;

    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

    private Estudiante crearEstudianteDummy() {
        Estudiante est = new Estudiante();
        est.setId(1);
        est.setNombreEstudiante("Juan Perez");
        est.setCorreoEstudiante("juan@mail.com");
        est.setIdUsuario(new Usuario()); // si quieres puedes crear un dummy Usuario aquí
        return est;
    }

    @Test
    @DisplayName("Agregar Estudiante")
    void testAddEstudiante() {
        Estudiante est = crearEstudianteDummy();

        when(estudianteRepository.save(any(Estudiante.class))).thenReturn(est);

        Estudiante resultado = estudianteService.addEstudiante(est);

        verify(estudianteRepository).save(est);
        assertEquals(est, resultado);
    }

    @Test
    @DisplayName("Eliminar Estudiante existente")
    void testDeleteEstudianteExistente() {
        when(estudianteRepository.existsById(1)).thenReturn(true);

        String resultado = estudianteService.deleteEstudiante(1);

        verify(estudianteRepository).deleteById(1);
        assertEquals("eliminado con exito", resultado);
    }

    @Test
    @DisplayName("Eliminar Estudiante inexistente")
    void testDeleteEstudianteInexistente() {
        when(estudianteRepository.existsById(99)).thenReturn(false);

        String resultado = estudianteService.deleteEstudiante(99);

        verify(estudianteRepository, never()).deleteById(anyInt());
        assertEquals("No se encuentra", resultado);
    }

    @Test
    @DisplayName("Actualizar Estudiante existente")
    void testUpdateEstudianteExistente() {
        Estudiante viejo = crearEstudianteDummy();
        viejo.setNombreEstudiante("Viejo Nombre");
        viejo.setCorreoEstudiante("viejo@mail.com");

        Estudiante nuevo = crearEstudianteDummy();
        nuevo.setNombreEstudiante("Nuevo Nombre");
        nuevo.setCorreoEstudiante("nuevo@mail.com");

        when(estudianteRepository.existsById(1)).thenReturn(true);
        when(estudianteRepository.findById(1)).thenReturn(Optional.of(viejo));

        String resultado = estudianteService.updateEstudiante(1, nuevo);

        verify(estudianteRepository).save(viejo);
        assertEquals(" actualizado con exito", resultado);
        assertEquals("Nuevo Nombre", viejo.getNombreEstudiante());
        assertEquals("nuevo@mail.com", viejo.getCorreoEstudiante());
    }

    @Test
    @DisplayName("Actualizar Estudiante inexistente")
    void testUpdateEstudianteInexistente() {
        Estudiante nuevo = crearEstudianteDummy();

        when(estudianteRepository.existsById(1)).thenReturn(false);

        String resultado = estudianteService.updateEstudiante(1, nuevo);

        verify(estudianteRepository, never()).save(any());
        assertEquals("No se encuentra ", resultado);
    }

    @Test
    @DisplayName("Obtener Estudiante existente")
    void testGetEstudianteExistente() {
        Estudiante est = crearEstudianteDummy();

        when(estudianteRepository.existsById(1)).thenReturn(true);
        when(estudianteRepository.findById(1)).thenReturn(Optional.of(est));

        Estudiante resultado = estudianteService.getEstudiante(1);

        assertEquals(est, resultado);
    }

    @Test
    @DisplayName("Obtener Estudiante inexistente")
    void testGetEstudianteInexistente() {
        when(estudianteRepository.existsById(1)).thenReturn(false);

        Estudiante resultado = estudianteService.getEstudiante(1);

        assertNull(resultado);
    }

    @Test
    @DisplayName("Obtener todos los Estudiantes")
    void testGetAllEstudiantes() {
        Estudiante e1 = crearEstudianteDummy();
        e1.setNombreEstudiante("Estudiante 1");

        Estudiante e2 = crearEstudianteDummy();
        e2.setNombreEstudiante("Estudiante 2");

        List<Estudiante> lista = Arrays.asList(e1, e2);

        when(estudianteRepository.findAll()).thenReturn(lista);

        List<Estudiante> resultado = estudianteService.getAllEstudiantes();

        assertEquals(2, resultado.size());
        assertEquals("Estudiante 1", resultado.get(0).getNombreEstudiante());
    }

    @Test
    @DisplayName("Test Controller GET /Estudiantes")
    void testGetAllEstudiantesController() throws Exception {
        Estudiante e1 = crearEstudianteDummy();
        e1.setNombreEstudiante("Estudiante 1");
        Estudiante e2 = crearEstudianteDummy();
        e2.setNombreEstudiante("Estudiante 2");

        List<Estudiante> lista = Arrays.asList(e1, e2);
        when(estudianteService.getAllEstudiantes()).thenReturn(lista);

        mockMvc.perform(get("/Estudiantes")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(lista.size()))
                .andExpect(jsonPath("$[0].nombreEstudiante").value("Estudiante 1"));
    }
}

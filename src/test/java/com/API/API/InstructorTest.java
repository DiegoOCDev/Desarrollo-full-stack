package com.API.API;

import com.API.API.model.Instructor;
import com.API.API.model.Usuario;
import com.API.API.repository.InstructorRepository;
import com.API.API.service.InstructorService;
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

public class InstructorTest {

    @MockitoBean
    private InstructorRepository instructorRepository;

    @Autowired
    private InstructorService instructorService;

    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private Instructor crearInstructorDummy() {
        Instructor instructor = new Instructor();
        instructor.setNombreInstructor("Luis Martínez");
        instructor.setCorreoInstructor("luis@correo.com");
        instructor.setIdUsuario(new Usuario()); // Dummy usuario
        return instructor;
    }

    // ===== Pruebas Servicio =====

    @Test
    @DisplayName("Agregar Instructor - Servicio")
    void testAddInstructor() {
        Instructor instructor = crearInstructorDummy();

        when(instructorRepository.save(instructor)).thenReturn(instructor);

        Instructor resultado = instructorService.addInstructor(instructor);

        verify(instructorRepository).save(instructor);
        assertEquals(instructor, resultado);
    }

    @Test
    @DisplayName("Eliminar Instructor existente - Servicio")
    void testDeleteInstructorExistente() {
        when(instructorRepository.existsById(1)).thenReturn(true);

        String resultado = instructorService.deleteInstructor(1);

        verify(instructorRepository).deleteById(1);
        assertEquals("eliminado con exito", resultado);
    }

    @Test
    @DisplayName("Eliminar Instructor inexistente - Servicio")
    void testDeleteInstructorInexistente() {
        when(instructorRepository.existsById(1)).thenReturn(false);

        String resultado = instructorService.deleteInstructor(1);

        verify(instructorRepository, never()).deleteById(anyInt());
        assertEquals("No se encuentra", resultado);
    }

    @Test
    @DisplayName("Actualizar Instructor existente - Servicio")
    void testUpdateInstructorExistente() {
        Instructor viejo = crearInstructorDummy();
        viejo.setNombreInstructor("Antiguo");
        viejo.setCorreoInstructor("antiguo@correo.com");

        Instructor nuevo = crearInstructorDummy();
        nuevo.setNombreInstructor("Nuevo");
        nuevo.setCorreoInstructor("nuevo@correo.com");

        when(instructorRepository.existsById(1)).thenReturn(true);
        when(instructorRepository.findById(1)).thenReturn(Optional.of(viejo));

        String resultado = instructorService.updateInstructor(1, nuevo);

        verify(instructorRepository).save(viejo);
        assertEquals(" actualizado con exito", resultado);
        assertEquals("Nuevo", viejo.getNombreInstructor());
        assertEquals("nuevo@correo.com", viejo.getCorreoInstructor());
    }

    @Test
    @DisplayName("Actualizar Instructor inexistente - Servicio")
    void testUpdateInstructorInexistente() {
        Instructor nuevo = crearInstructorDummy();

        when(instructorRepository.existsById(1)).thenReturn(false);

        String resultado = instructorService.updateInstructor(1, nuevo);

        verify(instructorRepository, never()).save(any());
        assertEquals("No se encuentra ", resultado);
    }

    @Test
    @DisplayName("Obtener Instructor existente - Servicio")
    void testGetInstructorExistente() {
        Instructor instructor = crearInstructorDummy();

        when(instructorRepository.existsById(1)).thenReturn(true);
        when(instructorRepository.findById(1)).thenReturn(Optional.of(instructor));

        Instructor resultado = instructorService.getInstructor(1);

        assertEquals(instructor, resultado);
    }

    @Test
    @DisplayName("Obtener Instructor inexistente - Servicio")
    void testGetInstructorInexistente() {
        when(instructorRepository.existsById(1)).thenReturn(false);

        Instructor resultado = instructorService.getInstructor(1);

        assertNull(resultado);
    }

    @Test
    @DisplayName("Obtener todos los Instructores - Servicio")
    void testGetAllInstructores() {
        Instructor i1 = crearInstructorDummy();
        Instructor i2 = crearInstructorDummy();
        i2.setNombreInstructor("Instructor B");

        List<Instructor> lista = Arrays.asList(i1, i2);

        when(instructorRepository.findAll()).thenReturn(lista);

        List<Instructor> resultado = instructorService.getAllIntructores();

        assertEquals(2, resultado.size());
        assertEquals("Luis Martínez", resultado.get(0).getNombreInstructor());
        assertEquals("Instructor B", resultado.get(1).getNombreInstructor());
    }

    // ===== Pruebas Controlador =====

    @Test
    @DisplayName("GET /Instructors - Obtener todos los instructores")
    void testGetAllInstructoresController() throws Exception {
        List<Instructor> lista = Arrays.asList(crearInstructorDummy(), crearInstructorDummy());
        when(instructorRepository.findAll()).thenReturn(lista);

        mockMvc.perform(get("/Instructors"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(lista.size()));
    }

    @Test
    @DisplayName("GET /Instructors/{id} - Instructor existente")
    void testGetInstructorPorIdControllerExistente() throws Exception {
        Instructor instructor = crearInstructorDummy();
        when(instructorRepository.existsById(1)).thenReturn(true);
        when(instructorRepository.findById(1)).thenReturn(Optional.of(instructor));

        mockMvc.perform(get("/Instructors/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombreInstructor").value("Luis Martínez"));
    }

    @Test
    @DisplayName("POST /Instructors - Crear instructor")
    void testAddInstructorController() throws Exception {
        Instructor instructor = crearInstructorDummy();
        when(instructorRepository.save(any())).thenReturn(instructor);

        mockMvc.perform(post("/Instructors")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(instructor)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nombreInstructor").value("Luis Martínez"));
    }

    @Test
    @DisplayName("PUT /Instructors/{id} - Actualizar instructor")
    void testUpdateInstructorController() throws Exception {
        Instructor viejo = crearInstructorDummy();
        Instructor nuevo = crearInstructorDummy();
        nuevo.setNombreInstructor("Actualizado");

        when(instructorRepository.existsById(1)).thenReturn(true);
        when(instructorRepository.findById(1)).thenReturn(Optional.of(viejo));
        when(instructorRepository.save(any())).thenReturn(viejo);

        mockMvc.perform(put("/Instructors/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(nuevo)))
                .andExpect(status().isOk())
                .andExpect(content().string(" actualizado con exito"));
    }
}

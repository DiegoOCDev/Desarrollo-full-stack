package com.API.API;

import com.API.API.model.Instructor;
import com.API.API.model.Usuario;
import com.API.API.repository.InstructorRepository;
import com.API.API.service.InstructorService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class InstructorTest {

    @MockitoBean
    private InstructorRepository instructorRepository;

    @MockitoBean
    private InstructorService instructorService;

    private Instructor crearInstructorDummy() {
        Instructor instructor = new Instructor();
        instructor.setNombreInstructor("Luis Martínez");
        instructor.setCorreoInstructor("luis@correo.com");
        instructor.setIdUsuario(new Usuario()); // Dummy usuario
        return instructor;
    }

    @Test
    void testAddInstructor() {
        Instructor instructor = crearInstructorDummy();

        when(instructorRepository.save(instructor)).thenReturn(instructor);

        Instructor resultado = instructorService.addInstructor(instructor);

        verify(instructorRepository).save(instructor);
        assertEquals(instructor, resultado);
    }

    @Test
    void testDeleteInstructorExistente() {
        when(instructorRepository.existsById(1)).thenReturn(true);

        String resultado = instructorService.deleteInstructor(1);

        verify(instructorRepository).deleteById(1);
        assertEquals("eliminado con exito", resultado);
    }

    @Test
    void testDeleteInstructorInexistente() {
        when(instructorRepository.existsById(1)).thenReturn(false);

        String resultado = instructorService.deleteInstructor(1);

        verify(instructorRepository, never()).deleteById(anyInt());
        assertEquals("No se encuentra", resultado);
    }

    @Test
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
    void testUpdateInstructorInexistente() {
        Instructor nuevo = crearInstructorDummy();

        when(instructorRepository.existsById(1)).thenReturn(false);

        String resultado = instructorService.updateInstructor(1, nuevo);

        verify(instructorRepository, never()).save(any());
        assertEquals("No se encuentra ", resultado);
    }

    @Test
    void testGetInstructorExistente() {
        Instructor instructor = crearInstructorDummy();

        when(instructorRepository.existsById(1)).thenReturn(true);
        when(instructorRepository.findById(1)).thenReturn(Optional.of(instructor));

        String resultado = instructorService.getInstructor(1);

        assertEquals(instructor.toString(), resultado);
    }

    @Test
    void testGetInstructorInexistente() {
        when(instructorRepository.existsById(1)).thenReturn(false);

        String resultado = instructorService.getInstructor(1);

        assertEquals("No se encuentra", resultado);
    }

    @Test
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
}

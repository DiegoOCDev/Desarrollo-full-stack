
package com.API.API;

import com.API.API.model.Estudiante;
import com.API.API.model.Usuario;
import com.API.API.repository.EstudianteRepository;
import com.API.API.service.EstudianteService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
        import static org.mockito.Mockito.*;

@SpringBootTest
public class EstudianteTest {

    @MockitoBean
    private EstudianteRepository estudianteRepository;

    @MockitoBean
    private EstudianteService estudianteService;

    @Test
    void testAddEstudiante() {
        Estudiante est = new Estudiante();
        est.setNombreEstudiante("Juan Perez");
        est.setCorreoEstudiante("juan@mail.com");
        est.setIdUsuario(new Usuario()); // Puedes crear un Usuario dummy si quieres

        when(estudianteRepository.save(est)).thenReturn(est);

        Estudiante resultado = estudianteService.addEstudiante(est);

        verify(estudianteRepository).save(est);
        assertEquals(est, resultado);
    }

    @Test
    void testDeleteEstudianteExistente() {
        when(estudianteRepository.existsById(1)).thenReturn(true);

        String resultado = estudianteService.deleteEstudiante(1);

        verify(estudianteRepository).deleteById(1);
        assertEquals("eliminado con exito", resultado);
    }

    @Test
    void testDeleteEstudianteInexistente() {
        when(estudianteRepository.existsById(99)).thenReturn(false);

        String resultado = estudianteService.deleteEstudiante(99);

        verify(estudianteRepository, never()).deleteById(anyInt());
        assertEquals("No se encuentra", resultado);
    }

    @Test
    void testUpdateEstudianteExistente() {
        Estudiante viejo = new Estudiante();
        viejo.setNombreEstudiante("Viejo Nombre");
        viejo.setCorreoEstudiante("viejo@mail.com");

        Estudiante nuevo = new Estudiante();
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
    void testUpdateEstudianteInexistente() {
        Estudiante nuevo = new Estudiante();
        nuevo.setNombreEstudiante("Nombre");
        nuevo.setCorreoEstudiante("correo@mail.com");

        when(estudianteRepository.existsById(1)).thenReturn(false);

        String resultado = estudianteService.updateEstudiante(1, nuevo);

        verify(estudianteRepository, never()).save(any());
        assertEquals("No se encuentra ", resultado);
    }

    @Test
    void testGetEstudianteExistente() {
        Estudiante est = new Estudiante();
        est.setNombreEstudiante("Estudiante Existente");
        est.setCorreoEstudiante("estudiante@mail.com");

        when(estudianteRepository.existsById(1)).thenReturn(true);
        when(estudianteRepository.findById(1)).thenReturn(Optional.of(est));

        String resultado = estudianteService.getEstudiante(1);

        assertEquals(est.toString(), resultado);
    }

    @Test
    void testGetEstudianteInexistente() {
        when(estudianteRepository.existsById(1)).thenReturn(false);

        String resultado = estudianteService.getEstudiante(1);

        assertEquals("No se encuentra", resultado);
    }

    @Test
    void testGetAllEstudiantes() {
        Estudiante e1 = new Estudiante();
        e1.setNombreEstudiante("Est1");

        Estudiante e2 = new Estudiante();
        e2.setNombreEstudiante("Est2");

        List<Estudiante> lista = Arrays.asList(e1, e2);

        when(estudianteRepository.findAll()).thenReturn(lista);

        List<Estudiante> resultado = estudianteService.getAllEstudiantes();

        assertEquals(2, resultado.size());
        assertEquals("Est1", resultado.get(0).getNombreEstudiante());
    }
}

package com.API.API;

import com.API.API.model.Soporte;
import com.API.API.repository.SoporteRepository;
import com.API.API.service.SoporteService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class SoporteTest {

    @MockitoBean
    private SoporteRepository soporteRepository;

    @MockitoBean
    private SoporteService soporteService;

    private Soporte crearSoporteDummy() {
        Soporte soporte = new Soporte();
        soporte.setNombreSoporte("Pedro Soporte");
        soporte.setCorreoSoporte("pedro@soporte.cl");
        return soporte;
    }

    @Test
    void testAddSoporte() {
        Soporte soporte = crearSoporteDummy();

        when(soporteRepository.save(soporte)).thenReturn(soporte);

        Soporte resultado = soporteService.addSoporte(soporte);

        verify(soporteRepository).save(soporte);
        assertEquals(soporte, resultado);
    }

    @Test
    void testDeleteSoporteExistente() {
        when(soporteRepository.existsById(1)).thenReturn(true);

        String resultado = soporteService.deleteSoporte(1);

        verify(soporteRepository).deleteById(1);
        assertEquals("eliminado con exito", resultado);
    }

    @Test
    void testDeleteSoporteInexistente() {
        when(soporteRepository.existsById(1)).thenReturn(false);

        String resultado = soporteService.deleteSoporte(1);

        verify(soporteRepository, never()).deleteById(anyInt());
        assertEquals("No se encuentra", resultado);
    }

    @Test
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
    void testUpdateSoporteInexistente() {
        Soporte nuevo = crearSoporteDummy();

        when(soporteRepository.existsById(1)).thenReturn(false);

        String resultado = soporteService.updateSoporte(1, nuevo);

        verify(soporteRepository, never()).save(any());
        assertEquals("No se encuentra ", resultado);
    }

    @Test
    void testGetSoporteExistente() {
        Soporte soporte = crearSoporteDummy();

        when(soporteRepository.existsById(1)).thenReturn(true);
        when(soporteRepository.findById(1)).thenReturn(Optional.of(soporte));

        String resultado = soporteService.getSoporte(1);

        assertEquals(soporte.toString(), resultado);
    }

    @Test
    void testGetSoporteInexistente() {
        when(soporteRepository.existsById(1)).thenReturn(false);

        String resultado = soporteService.getSoporte(1);

        assertEquals("No se encuentra", resultado);
    }

    @Test
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
}

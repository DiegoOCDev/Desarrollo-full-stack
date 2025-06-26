package com.API.API;

import com.API.API.model.Gerente;
import com.API.API.repository.GerenteRepository;
import com.API.API.service.GerenteService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class GerenteTest {

    @MockitoBean
    private GerenteRepository gerenteRepository;

    @MockitoBean
    private GerenteService gerenteService;

    private Gerente crearGerenteDummy() {
        Gerente gerente = new Gerente();
        gerente.setNombreGerente("María González");
        gerente.setCorreoGerente("maria@empresa.com");
        return gerente;
    }

    @Test
    void testAddGerente() {
        Gerente gerente = crearGerenteDummy();

        when(gerenteRepository.save(gerente)).thenReturn(gerente);

        Gerente resultado = gerenteService.addGerente(gerente);

        verify(gerenteRepository).save(gerente);
        assertEquals(gerente, resultado);
    }

    @Test
    void testDeleteGerenteExistente() {
        when(gerenteRepository.existsById(1)).thenReturn(true);

        String resultado = gerenteService.deleteGerente(1);

        verify(gerenteRepository).deleteById(1);
        assertEquals("eliminado con exito", resultado);
    }

    @Test
    void testDeleteGerenteInexistente() {
        when(gerenteRepository.existsById(1)).thenReturn(false);

        String resultado = gerenteService.deleteGerente(1);

        verify(gerenteRepository, never()).deleteById(anyInt());
        assertEquals("No se encuentra", resultado);
    }

    @Test
    void testUpdateGerenteExistente() {
        Gerente viejo = crearGerenteDummy();
        viejo.setNombreGerente("Antiguo Nombre");
        viejo.setCorreoGerente("viejo@mail.com");

        Gerente nuevo = crearGerenteDummy();
        nuevo.setNombreGerente("Nuevo Nombre");
        nuevo.setCorreoGerente("nuevo@mail.com");

        when(gerenteRepository.existsById(1)).thenReturn(true);
        when(gerenteRepository.findById(1)).thenReturn(Optional.of(viejo));

        String resultado = gerenteService.updateGerente(1, nuevo);

        verify(gerenteRepository).save(viejo);
        assertEquals(" actualizado con exito", resultado);
        assertEquals("Nuevo Nombre", viejo.getNombreGerente());
        assertEquals("nuevo@mail.com", viejo.getCorreoGerente());
    }

    @Test
    void testUpdateGerenteInexistente() {
        Gerente nuevo = crearGerenteDummy();

        when(gerenteRepository.existsById(1)).thenReturn(false);

        String resultado = gerenteService.updateGerente(1, nuevo);

        verify(gerenteRepository, never()).save(any());
        assertEquals("No se encuentra ", resultado);
    }

    @Test
    void testGetGerenteExistente() {
        Gerente gerente = crearGerenteDummy();

        when(gerenteRepository.existsById(1)).thenReturn(true);
        when(gerenteRepository.findById(1)).thenReturn(Optional.of(gerente));

        String resultado = gerenteService.getGerente(1);

        assertEquals(gerente.toString(), resultado);
    }

    @Test
    void testGetGerenteInexistente() {
        when(gerenteRepository.existsById(1)).thenReturn(false);

        String resultado = gerenteService.getGerente(1);

        assertEquals("No se encuentra", resultado);
    }

    @Test
    void testGetAllGerentes() {
        Gerente g1 = new Gerente();
        g1.setNombreGerente("Gerente Uno");

        Gerente g2 = new Gerente();
        g2.setNombreGerente("Gerente Dos");

        List<Gerente> lista = Arrays.asList(g1, g2);

        when(gerenteRepository.findAll()).thenReturn(lista);

        List<Gerente> resultado = gerenteService.getAllGerentes();

        assertEquals(2, resultado.size());
        assertEquals("Gerente Uno", resultado.get(0).getNombreGerente());
    }
}

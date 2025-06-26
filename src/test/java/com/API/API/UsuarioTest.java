package com.API.API;

import com.API.API.model.Usuario;
import com.API.API.repository.UsuarioRepository;
import com.API.API.service.UsuarioService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class UsuarioTest {

    @MockitoBean
    private UsuarioRepository usuarioRepository;

    @MockitoBean
    private UsuarioService usuarioService;

    private Usuario crearUsuarioDummy() {
        Usuario usuario = new Usuario();
        usuario.setNombreUsuario("Juan Pérez");
        usuario.setEmailUsuario("juan@example.com");
        usuario.setRolUsuario("Administrador");
        usuario.setContrasenaUsuario("1234");
        return usuario;
    }

    @Test
    void testAddUsuario() {
        Usuario usuario = crearUsuarioDummy();

        when(usuarioRepository.save(usuario)).thenReturn(usuario);

        Usuario resultado = usuarioService.addUsuario(usuario);

        verify(usuarioRepository).save(usuario);
        assertEquals(usuario, resultado);
    }

    @Test
    void testDeleteUsuarioExistente() {
        when(usuarioRepository.existsById(1)).thenReturn(true);

        String resultado = usuarioService.deleteUsuario(1);

        verify(usuarioRepository).deleteById(1);
        assertEquals("eliminado con exito", resultado);
    }

    @Test
    void testDeleteUsuarioInexistente() {
        when(usuarioRepository.existsById(1)).thenReturn(false);

        String resultado = usuarioService.deleteUsuario(1);

        verify(usuarioRepository, never()).deleteById(anyInt());
        assertEquals("No se encuentra", resultado);
    }

    @Test
    void testUpdateUsuarioExistente() {
        Usuario viejo = crearUsuarioDummy();
        viejo.setEmailUsuario("viejo@correo.com");

        Usuario nuevo = crearUsuarioDummy();
        nuevo.setNombreUsuario("Nuevo Nombre");
        nuevo.setEmailUsuario("nuevo@correo.com");
        nuevo.setContrasenaUsuario("nueva123");

        when(usuarioRepository.existsById(1)).thenReturn(true);
        when(usuarioRepository.findById(1)).thenReturn(Optional.of(viejo));

        String resultado = usuarioService.updateUsuario(1, nuevo);

        verify(usuarioRepository).save(viejo);
        assertEquals(" actualizado con exito", resultado);
        assertEquals("Nuevo Nombre", viejo.getNombreUsuario());
        assertEquals("nuevo@correo.com", viejo.getEmailUsuario());
        assertEquals("nueva123", viejo.getContrasenaUsuario());
    }

    @Test
    void testUpdateUsuarioInexistente() {
        Usuario nuevo = crearUsuarioDummy();

        when(usuarioRepository.existsById(1)).thenReturn(false);

        String resultado = usuarioService.updateUsuario(1, nuevo);

        verify(usuarioRepository, never()).save(any());
        assertEquals("No se encuentra ", resultado);
    }

    @Test
    void testGetUsuarioExistente() {
        Usuario usuario = crearUsuarioDummy();

        when(usuarioRepository.existsById(1)).thenReturn(true);
        when(usuarioRepository.findById(1)).thenReturn(Optional.of(usuario));

        String resultado = usuarioService.getUsuario(1);

        assertEquals(usuario.toString(), resultado);
    }

    @Test
    void testGetUsuarioInexistente() {
        when(usuarioRepository.existsById(1)).thenReturn(false);

        String resultado = usuarioService.getUsuario(1);

        assertEquals("No se encuentra", resultado);
    }

    @Test
    void testGetAllUsuarios() {
        Usuario u1 = crearUsuarioDummy();
        Usuario u2 = crearUsuarioDummy();
        u2.setNombreUsuario("Ana Soto");

        List<Usuario> lista = Arrays.asList(u1, u2);

        when(usuarioRepository.findAll()).thenReturn(lista);

        List<Usuario> resultado = usuarioService.getAllUsuarios();

        assertEquals(2, resultado.size());
        assertEquals("Juan Pérez", resultado.get(0).getNombreUsuario());
        assertEquals("Ana Soto", resultado.get(1).getNombreUsuario());
    }
}

package com.API.API;

import com.API.API.model.Usuario;
import com.API.API.repository.UsuarioRepository;
import com.API.API.service.UsuarioService;
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

public class UsuarioTest {

    @MockitoBean
    private UsuarioRepository usuarioRepository;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private Usuario crearUsuarioDummy() {
        Usuario usuario = new Usuario();
        usuario.setNombreUsuario("Juan Pérez");
        usuario.setEmailUsuario("juan@example.com");
        usuario.setRolUsuario("Administrador");
        usuario.setContrasenaUsuario("1234");
        return usuario;
    }

    // ==== Pruebas de servicio ====

    @Test
    @DisplayName("Agregar usuario - Servicio")
    void testAddUsuario() {
        Usuario usuario = crearUsuarioDummy();

        when(usuarioRepository.save(usuario)).thenReturn(usuario);

        Usuario resultado = usuarioService.addUsuario(usuario);

        verify(usuarioRepository).save(usuario);
        assertEquals(usuario, resultado);
    }

    @Test
    @DisplayName("Eliminar usuario existente - Servicio")
    void testDeleteUsuarioExistente() {
        when(usuarioRepository.existsById(1)).thenReturn(true);

        String resultado = usuarioService.deleteUsuario(1);

        verify(usuarioRepository).deleteById(1);
        assertEquals("eliminado con exito", resultado);
    }

    @Test
    @DisplayName("Eliminar usuario inexistente - Servicio")
    void testDeleteUsuarioInexistente() {
        when(usuarioRepository.existsById(1)).thenReturn(false);

        String resultado = usuarioService.deleteUsuario(1);

        verify(usuarioRepository, never()).deleteById(anyInt());
        assertEquals("No se encuentra", resultado);
    }

    @Test
    @DisplayName("Actualizar usuario existente - Servicio")
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
    @DisplayName("Actualizar usuario inexistente - Servicio")
    void testUpdateUsuarioInexistente() {
        Usuario nuevo = crearUsuarioDummy();

        when(usuarioRepository.existsById(1)).thenReturn(false);

        String resultado = usuarioService.updateUsuario(1, nuevo);

        verify(usuarioRepository, never()).save(any());
        assertEquals("No se encuentra ", resultado);
    }

    @Test
    @DisplayName("Obtener usuario existente - Servicio")
    void testGetUsuarioExistente() {
        Usuario usuario = crearUsuarioDummy();

        when(usuarioRepository.existsById(1)).thenReturn(true);
        when(usuarioRepository.findById(1)).thenReturn(Optional.of(usuario));

        Usuario resultado = usuarioService.getUsuario(1);

        assertEquals(usuario, resultado);
    }

    @Test
    @DisplayName("Obtener usuario inexistente - Servicio")
    void testGetUsuarioInexistente() {
        when(usuarioRepository.existsById(1)).thenReturn(false);

        Usuario resultado = usuarioService.getUsuario(1);

        assertNull(resultado);
    }

    @Test
    @DisplayName("Obtener todos los usuarios - Servicio")
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

    // ==== Pruebas controlador ====

    @Test
    @DisplayName("GET /Usuarios - Obtener todos los usuarios")
    void testGetAllUsuariosController() throws Exception {
        List<Usuario> lista = Arrays.asList(crearUsuarioDummy(), crearUsuarioDummy());
        when(usuarioRepository.findAll()).thenReturn(lista);

        mockMvc.perform(get("/Usuarios"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(lista.size()));
    }

    @Test
    @DisplayName("GET /Usuarios/{id} - Usuario existente")
    void testGetUsuarioPorIdControllerExistente() throws Exception {
        Usuario usuario = crearUsuarioDummy();
        when(usuarioRepository.existsById(1)).thenReturn(true);
        when(usuarioRepository.findById(1)).thenReturn(Optional.of(usuario));

        mockMvc.perform(get("/Usuarios/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombreUsuario").value("Juan Pérez"));
    }

    @Test
    @DisplayName("POST /Usuarios - Crear usuario")
    void testAddUsuarioController() throws Exception {
        Usuario usuario = crearUsuarioDummy();
        when(usuarioRepository.save(any())).thenReturn(usuario);

        mockMvc.perform(post("/Usuarios")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(usuario)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nombreUsuario").value("Juan Pérez"));
    }

    @Test
    @DisplayName("PUT /Usuarios/{id} - Actualizar usuario")
    void testUpdateUsuarioController() throws Exception {
        Usuario viejo = crearUsuarioDummy();
        Usuario nuevo = crearUsuarioDummy();
        nuevo.setNombreUsuario("Actualizado");

        when(usuarioRepository.existsById(1)).thenReturn(true);
        when(usuarioRepository.findById(1)).thenReturn(Optional.of(viejo));
        when(usuarioRepository.save(any())).thenReturn(viejo);

        mockMvc.perform(put("/Usuarios/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(nuevo)))
                .andExpect(status().isOk())
                .andExpect(content().string(" actualizado con exito"));
    }
}

package com.API.API;

import com.API.API.model.Admin;
import com.API.API.repository.AdminRepository;
import com.API.API.service.AdminService;
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
public class AdminTest {

    @MockitoBean
    private AdminRepository adminRepository;

    @Autowired
    private AdminService adminService;

    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private Admin crearAdminDummy() {
        Admin admin = new Admin();
        admin.setId(1);
        admin.setCorreoAdmin("admin@mail.com");
        admin.setNombreAdmin("Carlos");
        return admin;
    }

    // ------------------- Servicio -------------------

    @Test
    @DisplayName("Servicio - Agregar admin")
    void testAddAdmin() {
        Admin admin = crearAdminDummy();
        when(adminRepository.save(admin)).thenReturn(admin);

        Admin resultado = adminService.addAdmin(admin);

        verify(adminRepository).save(admin);
        assertEquals("Carlos", resultado.getNombreAdmin());
    }

    @Test
    @DisplayName("Servicio - Obtener admin por ID")
    void testGetAdminExistente() {
        Admin admin = crearAdminDummy();
        when(adminRepository.existsById(1)).thenReturn(true);
        when(adminRepository.findById(1)).thenReturn(Optional.of(admin));

        Admin resultado = adminService.getAdmin(1);

        assertNotNull(resultado);
        assertEquals("Carlos", resultado.getNombreAdmin());
    }

    @Test
    @DisplayName("Servicio - Actualizar admin existente")
    void testUpdateAdmin() {
        Admin viejo = crearAdminDummy();
        Admin nuevo = new Admin();
        nuevo.setNombreAdmin("Pedro");
        nuevo.setCorreoAdmin("nuevo@mail.com");

        when(adminRepository.existsById(1)).thenReturn(true);
        when(adminRepository.findById(1)).thenReturn(Optional.of(viejo));

        String result = adminService.updateAdmin(1, nuevo);

        verify(adminRepository).save(viejo);
        assertEquals(" actualizado con exito", result);
        assertEquals("Pedro", viejo.getNombreAdmin());
    }

    @Test
    @DisplayName("Servicio - Eliminar admin")
    void testDeleteAdmin() {
        when(adminRepository.existsById(1)).thenReturn(true);

        String msg = adminService.deleteAdmin(1);

        verify(adminRepository).deleteById(1);
        assertEquals("eliminado con exito", msg);
    }

    // ------------------- Controlador -------------------

    @Test
    @DisplayName("Controlador - GET /Admins")
    void testGetAllAdminsController() throws Exception {
        List<Admin> lista = Arrays.asList(crearAdminDummy(), crearAdminDummy());
        when(adminRepository.findAll()).thenReturn(lista);

        mockMvc.perform(get("/Admins"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].nombreAdmin").value("Carlos"));
    }

    @Test
    @DisplayName("Controlador - GET /Admins/{id}")
    void testGetAdminByIdController() throws Exception {
        Admin admin = crearAdminDummy();
        when(adminRepository.existsById(1)).thenReturn(true);
        when(adminRepository.findById(1)).thenReturn(Optional.of(admin));

        mockMvc.perform(get("/Admins/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombreAdmin").value("Carlos"));
    }

    @Test
    @DisplayName("Controlador - POST /Admins")
    void testAddAdminController() throws Exception {
        Admin admin = crearAdminDummy();
        when(adminRepository.save(any(Admin.class))).thenReturn(admin);

        mockMvc.perform(post("/Admins")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(admin)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.correoAdmin").value("admin@mail.com"));
    }

    @Test
    @DisplayName("Controlador - DELETE /Admins/{id}")
    void testDeleteAdminController() throws Exception {
        when(adminRepository.existsById(1)).thenReturn(true);

        mockMvc.perform(delete("/Admins/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("eliminado con exito"));
    }
}

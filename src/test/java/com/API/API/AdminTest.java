
package com.API.API;

import com.API.API.model.Admin;
import com.API.API.repository.AdminRepository;
import com.API.API.service.AdminService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
        import static org.mockito.Mockito.*;

@SpringBootTest
public class AdminTest {

    @MockitoBean
    private AdminRepository adminRepository;

    @MockitoBean
    private AdminService adminService;

    @BeforeEach
    void setup() {
        // Si quieres, puedes limpiar mocks aquí o configurar comportamiento común
    }

    @Test
    void testAddAdmin() {
        Admin admin = new Admin();
        admin.setCorreoAdmin("admin@mail.com");
        admin.setNombreAdmin("Carlos");

        when(adminRepository.save(admin)).thenReturn(admin);

        Admin resultado = adminService.addAdmin(admin);

        verify(adminRepository).save(admin);
        assertEquals(admin, resultado);
    }

    @Test
    void testDeleteAdminExistente() {
        when(adminRepository.existsById(1)).thenReturn(true);

        String resultado = adminService.deleteAdmin(1);

        verify(adminRepository).deleteById(1);
        assertEquals("eliminado con exito", resultado);
    }

    @Test
    void testDeleteAdminInexistente() {
        when(adminRepository.existsById(99)).thenReturn(false);

        String resultado = adminService.deleteAdmin(99);

        verify(adminRepository, never()).deleteById(anyInt());
        assertEquals("No se encuentra", resultado);
    }

    @Test
    void testUpdateAdminExistente() {
        Admin viejo = new Admin();
        viejo.setCorreoAdmin("old@mail.com");
        viejo.setNombreAdmin("Juan");

        Admin nuevo = new Admin();
        nuevo.setCorreoAdmin("new@mail.com");
        nuevo.setNombreAdmin("Pedro");

        when(adminRepository.existsById(1)).thenReturn(true);
        when(adminRepository.findById(1)).thenReturn(Optional.of(viejo));

        String resultado = adminService.updateAdmin(1, nuevo);

        verify(adminRepository).save(viejo);
        assertEquals(" actualizado con exito", resultado);
        assertEquals("Pedro", viejo.getNombreAdmin());
        assertEquals("new@mail.com", viejo.getCorreoAdmin());
    }

    @Test
    void testUpdateAdminInexistente() {
        Admin nuevo = new Admin();
        nuevo.setCorreoAdmin("new@mail.com");
        nuevo.setNombreAdmin("Pedro");

        when(adminRepository.existsById(1)).thenReturn(false);

        String resultado = adminService.updateAdmin(1, nuevo);

        verify(adminRepository, never()).save(any());
        assertEquals("No se encuentra ", resultado);
    }

    @Test
    void testGetAdminExistente() {
        Admin admin = new Admin();
        admin.setCorreoAdmin("admin@mail.com");
        admin.setNombreAdmin("Lucía");

        when(adminRepository.existsById(1)).thenReturn(true);
        when(adminRepository.findById(1)).thenReturn(Optional.of(admin));

        String resultado = adminService.getAdmin(1);

        assertEquals(admin.toString(), resultado);
    }

    @Test
    void testGetAdminInexistente() {
        when(adminRepository.existsById(1)).thenReturn(false);

        String resultado = adminService.getAdmin(1);

        assertEquals("No se encuentra", resultado);
    }

    @Test
    void testGetAllAdmins() {
        Admin a1 = new Admin();
        a1.setCorreoAdmin("uno@mail.com");
        a1.setNombreAdmin("Uno");

        Admin a2 = new Admin();
        a2.setCorreoAdmin("dos@mail.com");
        a2.setNombreAdmin("Dos");

        List<Admin> lista = Arrays.asList(a1, a2);

        when(adminRepository.findAll()).thenReturn(lista);

        List<Admin> resultado = adminService.getAllAdmins();

        assertEquals(2, resultado.size());
        assertEquals("Uno", resultado.get(0).getNombreAdmin());
    }
}

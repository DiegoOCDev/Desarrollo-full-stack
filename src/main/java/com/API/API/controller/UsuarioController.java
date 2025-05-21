package com.API.API.controller;

import com.API.API.model.LoginRequest;
import com.API.API.model.Usuario;
import com.API.API.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/Usuarios")

class UsuarioController {
    @Autowired
    private UsuarioService usuarioService;
    @GetMapping
    public List<Usuario> listar() {
        return  usuarioService.getAllUsuarios();
    }
    @GetMapping("/{id}")
    public String getUsuario(@PathVariable int id) {
        return usuarioService.getUsuario(id);
    }
    @GetMapping("/SingUp")
    public Usuario SingUp(@RequestBody LoginRequest form) {
        String contrasena = form.getPassword();
        String usuario = form.getUsername();
        List<Usuario> usuarios = usuarioService.getAllUsuarios();
        for (Usuario usuario1 : usuarios) {
            if(usuario1.getEmailUsuario().equals(usuario)) {
                if(usuario1.getContrasenaUsuario().equals(contrasena) ) {
                    return usuario1;
                }
            }
        }
        return null;

    }

    @PostMapping
    public Usuario addUsuario(@RequestBody Usuario usuario) {
        return usuarioService.addUsuario(usuario);
    }
    @PutMapping("/{id}")
    public String updateUsuario(@PathVariable int id, @RequestBody Usuario usuario) {
        return usuarioService.updateUsuario(id, usuario);
    }
    @DeleteMapping("/{id}")
    public String deleteUsuario(@PathVariable int id) {
        return usuarioService.deleteUsuario(id);
    }

}

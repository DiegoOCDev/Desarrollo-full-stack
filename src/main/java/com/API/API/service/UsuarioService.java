package com.API.API.service;
import java.util.List;

import com.API.API.model.Soporte;
import com.API.API.model.Usuario;
import com.API.API.repository.SoporteRepository;
import org.springframework.stereotype.Service;
import com.API.API.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class UsuarioService {
    @Autowired
    private UsuarioRepository repository;

    public Usuario addUsuario(Usuario usuario) {
        repository.save(usuario);
        return usuario;
    }

    public String deleteUsuario(int id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return "eliminado con exito";
        } else {
            return "No se encuentra";
        }
    }

    public String updateUsuario(int id, Usuario usuario) {
        if (repository.existsById(id)) {
            Usuario buscado = repository.findById(id).get();
            buscado.setContrasenaUsuario(usuario.getContrasenaUsuario());
            buscado.setNombreUsuario(usuario.getNombreUsuario());
            buscado.setEmailUsuario(usuario.getEmailUsuario());
            repository.save(buscado);

            return " actualizado con exito";
        } else {
            return "No se encuentra ";
        }

    }

    public String getUsuario(int id) {
        String output = "";
        if (repository.existsById(id)) {
            Usuario usuario = repository.findById(id).get();
            output = usuario.toString();
            return output;
        } else {
            return "No se encuentra";
        }
    }

    public List<Usuario> getAllUsuarios() {
        return repository.findAll();
    }
}

package com.API.API.controller;

import com.API.API.model.Curso;
import com.API.API.model.Evaluacion;
import com.API.API.model.LoginRequest;
import com.API.API.model.Usuario;
import com.API.API.service.UsuarioService;
import io.swagger.v3.oas.annotations.*;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/Usuarios")
@Tag(name = "Controlador Usuarios", description = "API para gestión de usuarios en el sistema")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping
    @Operation(summary = "Listar todos los usuarios", description = "Obtiene la lista completa de usuarios registrados")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de usuarios obtenida",
                    content = @Content(mediaType = "application/json",  array = @ArraySchema(schema = @Schema(implementation = Usuario.class)))),
            @ApiResponse(responseCode = "404", description = "No se encontraron usuarios", content = @Content)
    })
    public ResponseEntity<List<Usuario>> listar() {
        List<Usuario> lista = usuarioService.getAllUsuarios();
        if (lista.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(lista, HttpStatus.OK);
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener usuario por ID", description = "Obtiene un usuario específico mediante su ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Usuario encontrado",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Usuario.class))),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado",content = @Content)
    })
    public ResponseEntity<Usuario> getUsuario(
            @Parameter(description = "ID del usuario", example = "1")
            @PathVariable int id) {
        Usuario resultado = usuarioService.getUsuario(id);
        if (resultado == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(resultado, HttpStatus.OK);
    }

    @PostMapping("/SingUp")
    @Operation(summary = "Login de usuario", description = "Permite al usuario autenticarse mediante usuario y contraseña")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Login exitoso",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Usuario.class))),
            @ApiResponse(responseCode = "401", description = "Credenciales inválidas", content = @Content)
    })
    public ResponseEntity<Usuario> SingUp(
            @RequestBody(description = "Datos de login", required = true,
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = LoginRequest.class)))
            @org.springframework.web.bind.annotation.RequestBody LoginRequest form) {

        String con = form.getPassword();
        String usuario = form.getUsername();
        List<Usuario> usuarios = usuarioService.getAllUsuarios();
        for (Usuario usuario1 : usuarios) {
            if (usuario1.getEmailUsuario().equals(usuario)) {
                if (usuario1.getContrasenaUsuario().equals(con)) {
                    return new ResponseEntity<>(usuario1, HttpStatus.OK);
                }
            }
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @PostMapping
    @Operation(summary = "Crear nuevo usuario", description = "Registra un nuevo usuario en el sistema")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Usuario creado exitosamente",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Usuario.class))),
            @ApiResponse(responseCode = "400", description = "Datos inválidos en la solicitud", content = @Content)
    })
    public ResponseEntity<Usuario> addUsuario(
            @RequestBody(description = "Datos del usuario a crear", required = true,
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Usuario.class)))
            @org.springframework.web.bind.annotation.RequestBody Usuario usuario) {
        Usuario creado = usuarioService.addUsuario(usuario);
        return new ResponseEntity<>(creado, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar usuario", description = "Actualiza la información de un usuario existente")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Usuario actualizado correctamente"),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    public ResponseEntity<String> updateUsuario(
            @Parameter(description = "ID del usuario a actualizar", example = "1")
            @PathVariable int id,
            @RequestBody(description = "Datos actualizados del usuario", required = true,
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Usuario.class)))
            @org.springframework.web.bind.annotation.RequestBody Usuario usuario) {
        String resultado = usuarioService.updateUsuario(id, usuario);
        if (resultado.trim().startsWith("No se encuentra")) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(resultado, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar usuario por ID", description = "Elimina un usuario específico usando su ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Usuario eliminado correctamente"),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    public ResponseEntity<String> deleteUsuario(
            @Parameter(description = "ID del usuario a eliminar", example = "1")
            @PathVariable int id) {
        String resultado = usuarioService.deleteUsuario(id);
        if ("No se encuentra".equals(resultado)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(resultado, HttpStatus.OK);
    }
}

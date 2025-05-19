package com.API.API.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "usuario")
public class Usuario {
    @Id
    @Column(name = "idUsuario", nullable = false)
    private Integer idUsuario;

    @Column(name = "nombreUsuario", nullable = false, length = 100)
    private String nombreUsuario;

    @Column(name = "emailUsuario", nullable = false, length = 100)
    private String emailUsuario;

    @Column(name = "rolUsuario", nullable = false, length = 100)
    private String rolUsuario;

    @Column(name = "contrasenaUsuario", nullable = false, length = 100)
    private String contrasenaUsuario;

}
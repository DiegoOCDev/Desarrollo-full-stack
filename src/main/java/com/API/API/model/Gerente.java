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
@Table(name = "gerente")
public class Gerente {
    @Id
    @Column(name = "idGerente", nullable = false)
    private Integer idGerente;

    @Column(name = "nombreGerente", nullable = false, length = 100)
    private String nombreGerente;

    @Column(name = "correoGerente", nullable = false, length = 100)
    private String correoGerente;

}
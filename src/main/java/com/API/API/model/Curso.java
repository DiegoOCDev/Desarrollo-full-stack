package com.API.API.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "curso")
public class Curso {
    @Id
    @Column(name = "idCurso", nullable = false)
    private Integer idCurso;

    @Column(name = "tituloCurso", nullable = false, length = 100)
    private String tituloCurso;

    @Column(name = "descripcionCurso", nullable = false, length = 100)
    private String descripcionCurso;

    @Column(name = "estadoCurso", nullable = false, length = 100)
    private String estadoCurso;

    @Column(name = "fecha_inicio")
    private LocalDate fechaInicio;

    @Column(name = "fecha_fin")
    private LocalDate fechaFin;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idGerente")
    private Gerente idGerente;

}
package com.API.API.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "curso")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Curso {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idCurso", nullable = false)
    @JsonIgnore

    private Integer id;

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

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "idGerente", nullable = false)
    private Gerente idGerente;

}
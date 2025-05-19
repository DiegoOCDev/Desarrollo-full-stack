package com.API.API.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "evaluacion")
public class Evaluacion {
    @Id
    @Column(name = "idEvaluacion", nullable = false)
    private Integer idEvaluacion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idCurso")
    private Curso idCurso;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idEstudiante")
    private Estudiante idEstudiante;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idInstructor")
    private Instructor idInstructor;

    @Column(name = "notaEvaluacion", nullable = false)
    private Integer notaEvaluacion;

}
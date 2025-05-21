package com.API.API.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
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
    @JsonIgnore


    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "idCurso", nullable = false)
    private Curso idCurso;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "idEstudiante", nullable = false)
    private Estudiante idEstudiante;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "idInstructor", nullable = false)
    private Instructor idInstructor;

    @Column(name = "notaEvaluacion", nullable = false)
    private Integer notaEvaluacion;

}
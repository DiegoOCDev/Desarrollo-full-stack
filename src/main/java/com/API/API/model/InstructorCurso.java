package com.API.API.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "instructor_curso")
public class InstructorCurso {
    @EmbeddedId
    private InstructorCursoId id;

    @MapsId("idInstructor")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "idInstructor", nullable = false)
    private Instructor idInstructor;

    @MapsId("idCurso")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "idCurso", nullable = false)
    private Curso idCurso;

}
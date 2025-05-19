package com.API.API.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.Hibernate;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@Embeddable
public class InstructorCursoId implements Serializable {
    private static final long serialVersionUID = -8458762449230012532L;
    @Column(name = "idInstructor", nullable = false)
    private Integer idInstructor;

    @Column(name = "idCurso", nullable = false)
    private Integer idCurso;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        InstructorCursoId entity = (InstructorCursoId) o;
        return Objects.equals(this.idInstructor, entity.idInstructor) &&
                Objects.equals(this.idCurso, entity.idCurso);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idInstructor, idCurso);
    }

}
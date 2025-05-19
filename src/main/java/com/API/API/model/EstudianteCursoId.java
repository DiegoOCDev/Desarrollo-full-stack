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
public class EstudianteCursoId implements Serializable {
    private static final long serialVersionUID = 8086620450578334454L;
    @Column(name = "idEstudiante", nullable = false)
    private Integer idEstudiante;

    @Column(name = "idCurso", nullable = false)
    private Integer idCurso;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        EstudianteCursoId entity = (EstudianteCursoId) o;
        return Objects.equals(this.idCurso, entity.idCurso) &&
                Objects.equals(this.idEstudiante, entity.idEstudiante);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idCurso, idEstudiante);
    }

}
package com.API.API.repository;

import com.API.API.model.EstudianteCurso;
import com.API.API.model.EstudianteCursoId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EstudianteCursoRepository extends JpaRepository<EstudianteCurso, EstudianteCursoId> {
}
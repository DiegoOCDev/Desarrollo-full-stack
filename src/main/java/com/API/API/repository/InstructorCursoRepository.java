package com.API.API.repository;

import com.API.API.model.InstructorCurso;
import com.API.API.model.InstructorCursoId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InstructorCursoRepository extends JpaRepository<InstructorCurso, InstructorCursoId> {
}
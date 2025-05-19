package com.API.API.service;
import java.util.List;

import com.API.API.model.Evaluacion;
import com.API.API.model.Instructor;
import com.API.API.repository.EvaluacionRepository;
import org.springframework.stereotype.Service;
import com.API.API.repository.InstructorRepository;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class InstructorService {
    @Autowired
    private InstructorRepository repository;

    public Instructor addInstructor(Instructor instructor) {
        repository.save(instructor);
        return instructor;
    }

    public String deleteInstructor(int id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return "eliminado con exito";
        } else {
            return "No se encuentra";
        }
    }

    public String updateInstructor(int id, Instructor instructor) {
        if (repository.existsById(id)) {
            Instructor buscado = repository.findById(id).get();
            buscado.setCorreoInstructor(instructor.getCorreoInstructor());
            buscado.setNombreInstructor(instructor.getNombreInstructor());

            repository.save(buscado);
            return " actualizado con exito";
        } else {
            return "No se encuentra ";
        }

    }

    public String getInstructor(int id) {
        String output = "";
        if (repository.existsById(id)) {
            Instructor instructor = repository.findById(id).get();
            output = instructor.toString();
            return output;
        } else {
            return "No se encuentra";
        }
    }

    public List<Instructor> getAllIntructores() {
        return repository.findAll();
    }
}
package com.API.API.service;
import java.util.List;

import com.API.API.model.Estudiante;
import com.API.API.repository.CursoRepository;
import org.springframework.stereotype.Service;
import com.API.API.repository.EstudianteRepository;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class EstudianteService {
    @Autowired
    private EstudianteRepository repository;

    public Estudiante addEstudiante(Estudiante estudiante) {
        repository.save(estudiante);
        return estudiante;
    }
    public String deleteEstudiante(int id) {
        if(repository.existsById(id)){
            repository.deleteById(id);
            return "eliminado con exito";
        }else{
            return "No se encuentra";
        }
    }
    public String updateEstudiante(int id,Estudiante estudiante) {
        if(repository.existsById(id)){
            Estudiante buscado =  repository.findById(id).get();
            buscado.setCorreoEstudiante(estudiante.getCorreoEstudiante());
            buscado.setNombreEstudiante(estudiante.getNombreEstudiante());
            repository.save(buscado);
            return " actualizado con exito";
        }else{
            return  "No se encuentra ";
        }

    }
    public String getEstudiante(int id){
        String output="";
        if(repository.existsById(id)){
            Estudiante  estudiante = repository.findById(id).get();
            output = estudiante.toString();
            return output;
        }else{
            return "No se encuentra";
        }
    }
    public List<Estudiante> getAllEstudiantes() {
        return repository.findAll();
    }
}

package com.API.API.service;
import java.util.List;

import com.API.API.model.Evaluacion;
import org.springframework.stereotype.Service;
import com.API.API.repository.EvaluacionRepository;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class EvaluacionService {
    @Autowired
    private EvaluacionRepository repository;
    public Evaluacion addEvaluacion(Evaluacion evaluacion) {
        repository.save(evaluacion);
        return evaluacion;
    }
    public String deleteEvaluacion(int id) {
        if(repository.existsById(id)){
            repository.deleteById(id);
            return "eliminado con exito";
        }else{
            return "No se encuentra";
        }
    }
    public String updateEvaluacion(int id, Evaluacion evaluacion) {
        if(repository.existsById(id)){
            Evaluacion buscado =  repository.findById(id).get();
            buscado.setNotaEvaluacion(evaluacion.getNotaEvaluacion());
            repository.save(buscado);
            return " actualizado con exito";
        }else{
            return  "No se encuentra ";
        }

    }
    public Evaluacion getEvaluacion(int id){
        String output="";
        if(repository.existsById(id)){
            Evaluacion  evaluacion = repository.findById(id).get();
            output = evaluacion.toString();
            return evaluacion;
        }else{
            return null;
        }
    }
    public List<Evaluacion> getAllEvaluaciones() {
        return repository.findAll();
    }
}

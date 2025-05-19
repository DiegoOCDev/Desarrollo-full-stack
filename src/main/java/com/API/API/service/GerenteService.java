package com.API.API.service;
import java.util.List;

import com.API.API.model.Evaluacion;
import com.API.API.model.Gerente;
import com.API.API.repository.GerenteRepository;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class GerenteService {
    @Autowired
    private GerenteRepository repository;
    public Gerente addGerente(Gerente gerente) {
        repository.save(gerente);
        return gerente;
    }
    public String deleteGerente(int id) {
        if(repository.existsById(id)){
            repository.deleteById(id);
            return "eliminado con exito";
        }else{
            return "No se encuentra";
        }
    }
    public String updateGerente(int id,Gerente gerente) {
        if(repository.existsById(id)){
            Gerente buscado =  repository.findById(id).get();
            buscado.setCorreoGerente(gerente.getCorreoGerente());
            buscado.setNombreGerente(gerente.getNombreGerente());
            repository.save(buscado);
            return " actualizado con exito";
        }else{
            return  "No se encuentra ";
        }

    }
    public String getGerente(int id){
        String output="";
        if(repository.existsById(id)){
            Gerente evaluacion = repository.findById(id).get();
            output = evaluacion.toString();
            return output;
        }else{
            return "No se encuentra";
        }
    }
    public List<Gerente> getAllGerentes() {
        return repository.findAll();
    }
}

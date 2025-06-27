package com.API.API.service;
import java.util.List;

import com.API.API.model.Admin;
import com.API.API.model.Curso;
import com.API.API.repository.AdminRepository;
import org.springframework.stereotype.Service;
import com.API.API.repository.CursoRepository;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class CursoService {
    @Autowired
    private CursoRepository repository;
    public Curso addCurso(Curso curso) {
        repository.save(curso);
        return curso;
    }
    public String deleteCurso(int id) {
        if(repository.existsById(id)){
            repository.deleteById(id);
            return "eliminado con exito";
        }else{
            return "No se encuentra";
        }
    }
    public String updateCurso(int id,Curso curso) {
        if(repository.existsById(id)){
            Curso buscado =  repository.findById(id).get();
            buscado.setDescripcionCurso(curso.getDescripcionCurso());
            buscado.setEstadoCurso(curso.getEstadoCurso());
            buscado.setTituloCurso(curso.getTituloCurso());
            buscado.setFechaInicio(curso.getFechaInicio());
            buscado.setFechaFin(curso.getFechaFin());
            repository.save(buscado);
            return " actualizado con exito";
        }else{
            return  "No se encuentra ";
        }

    }
    public Curso getCurso(int id){
        String output="";
        if(repository.existsById(id)){
            Curso  clase = repository.findById(id).get();
            output = clase.toString();
            return clase;
        }else{
            return null;
        }
    }
    public List<Curso> getAllCursos() {
        return repository.findAll();
    }
}

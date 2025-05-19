package com.API.API.controller;


import com.API.API.model.Curso;
import org.springframework.web.bind.annotation.RestController;
import com.API.API.service.CursoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/Cursos")

class CursoController {
    @Autowired
    private CursoService cursoService;
    @GetMapping
    public List<Curso> getCursos(){
        return cursoService.getAllCursos();
    }

    @GetMapping("/{id}")
    public String getCurso(@PathVariable Integer id) {
        return cursoService.getCurso(id);
    }
    @PostMapping
    public Curso addCurso(@RequestBody Curso curso){

        return cursoService.addCurso(curso);

    }
    @DeleteMapping("/{id}")
    public String deleteCurso(@PathVariable Integer id) {
        return cursoService.deleteCurso(id);
    }
    @PutMapping("/{id}")
    public String updateCurso(@PathVariable Integer id, @RequestBody Curso curso){
        return cursoService.updateCurso(id,curso);
    }


}

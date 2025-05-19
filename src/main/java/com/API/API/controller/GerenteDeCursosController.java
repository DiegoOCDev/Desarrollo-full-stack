package com.API.API.controller;

import com.API.API.model.Gerente;
import org.springframework.web.bind.annotation.RestController;
import com.API.API.service.GerenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/GerenteDeCursos")

class GerenteDeCursosController {
    @Autowired
    GerenteService gerenteService;
    @GetMapping("/GetAll")
    public List<Gerente> getGerenteDeCursos(){
        return gerenteService.getAllGerentes();
    }
    @GetMapping("/{id}")
    public Gerente getGerenteDeCursos(@PathVariable int id) {
        return gerenteService.getGerente(id);
    }
    @PostMapping
    public Gerente addGerenteDeCursos(@RequestBody Gerente gerenteDeCursos) {
        return gerenteService.addGerente(gerenteDeCursos);
    }
    @PutMapping("/{id}")
    public String updateGerenteDeCursos(@PathVariable int id, @RequestBody Gerente gerenteDeCursos) {
        return gerenteService.updateGerente(id, gerenteDeCursos);
    }
    @DeleteMapping("/{id}")
    public String deleteGerenteDeCursos(@PathVariable int id) {
        return gerenteService.deleteGerente(id);
    }

}

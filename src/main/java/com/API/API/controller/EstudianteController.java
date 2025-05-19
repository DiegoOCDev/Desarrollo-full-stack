package com.API.API.controller;

import com.API.API.model.Estudiante;
import org.springframework.web.bind.annotation.RestController;
import com.API.API.service.EstudianteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/Estudiantes")

class EstudianteController {
    @Autowired
    EstudianteService estudianteService;
    @GetMapping
    public List<Estudiante> getEstudiantes() {
        return estudianteService.getAllEstudiantes();
    }
    @GetMapping("/{id}")
    public String getEstudiante(@PathVariable int id) {
        return estudianteService.getEstudiante(id);
    }
    @PutMapping("/{id}")
    public String updateEstudiante(@RequestBody Estudiante estudiante, @PathVariable int id) {
        return estudianteService.updateEstudiante(id,estudiante);
    }
    @DeleteMapping("/{id}")
    public String deleteEstudiante(@PathVariable int id) {
        return estudianteService.deleteEstudiante(id);
    }
    @PostMapping
    public Estudiante addEstudiante(@RequestBody Estudiante estudiante) {
        return estudianteService.addEstudiante(estudiante);
    }


}

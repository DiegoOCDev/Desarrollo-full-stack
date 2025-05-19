package com.API.API.controller;

import com.API.API.model.Reporte;
import org.springframework.web.bind.annotation.RestController;
import com.API.API.service.ReporteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/Reportes")

class ReporteController {
    @Autowired
    private ReporteService reporteService;
    @GetMapping("/GetAll")
    public List<Reporte> getReportes() {
        return reporteService.getAllReportes();
    }
    @GetMapping("/{id}")
    public Reporte  getReporteById(@PathVariable int id) {
        return reporteService.getReporte(id);
    }
    @PostMapping
    public Reporte addReporte(@RequestBody Reporte reporte) {
        return reporteService.addReporte(reporte);
    }
    @PutMapping("/{id}")
    public String updateReporte(@PathVariable int id, @RequestBody Reporte reporte) {
        return reporteService.updateReporte(id,reporte);
    }
    @DeleteMapping("/{id}")
    public String deleteReporte(@PathVariable int id) {
        return reporteService.deleteReporte(id);
    }

}

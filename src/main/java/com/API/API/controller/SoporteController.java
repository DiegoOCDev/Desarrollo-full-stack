package com.API.API.controller;

import com.API.API.model.Soporte;
import org.springframework.web.bind.annotation.RestController;
import com.API.API.service.SoporteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/Soportes")

class SoporteController {
    @Autowired
    SoporteService soporteService;
    @GetMapping
    public List<Soporte> listar() {
        return  soporteService.getAllSoportes();
    }
    @GetMapping("/{id}")
    public String getSoporte(@PathVariable int id) {
        return soporteService.getSoporte(id);
    }
    @PostMapping
    public Soporte addSoporte(@RequestBody Soporte soporte) {
        return soporteService.addSoporte(soporte);
    }
    @PutMapping("/{id}")
    public String updateSoporte(@RequestBody Soporte soporte, @PathVariable int id){
        return soporteService.updateSoporte(id,soporte );
    }
    @DeleteMapping("/{id]")
    public String deleteSoporte(@PathVariable int id) {
       return  soporteService.deleteSoporte(id);
    }


}

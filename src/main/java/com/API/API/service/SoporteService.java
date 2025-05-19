package com.API.API.service;
import java.util.List;

import com.API.API.model.Reporte;
import com.API.API.model.Soporte;
import com.API.API.repository.ReporteRepository;
import org.springframework.stereotype.Service;
import com.API.API.repository.SoporteRepository;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class SoporteService {
    @Autowired
    private SoporteRepository repository;

    public Soporte addSoporte(Soporte soporte) {
        repository.save(soporte);
        return soporte;
    }

    public String deleteSoporte(int id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return "eliminado con exito";
        } else {
            return "No se encuentra";
        }
    }

    public String updateSoporte(int id, Soporte soporte) {
        if (repository.existsById(id)) {
            Soporte buscado = repository.findById(id).get();
            buscado.setCorreoSoporte(soporte.getCorreoSoporte());
            buscado.setNombreSoporte(soporte.getNombreSoporte());

            repository.save(buscado);
            return " actualizado con exito";
        } else {
            return "No se encuentra ";
        }

    }

    public String getSoporte(int id) {
        String output = "";
        if (repository.existsById(id)) {
            Soporte soporte = repository.findById(id).get();
            output = soporte.toString();
            return output;
        } else {
            return "No se encuentra";
        }
    }

    public List<Soporte> getAllSoportes() {
        return repository.findAll();
    }
}

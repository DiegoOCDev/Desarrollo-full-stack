package com.API.API.service;
import java.util.List;

import com.API.API.model.Reporte;
import org.springframework.stereotype.Service;
import com.API.API.repository.ReporteRepository;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class ReporteService {
    @Autowired
    private ReporteRepository repository;

    public Reporte addReporte(Reporte reporte) {
        repository.save(reporte);
        return reporte;
    }

    public String deleteReporte(int id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return "eliminado con exito";
        } else {
            return "No se encuentra";
        }
    }

    public String updateReporte(int id, Reporte Reporte) {
        if (repository.existsById(id)) {
            Reporte buscado = repository.findById(id).get();
            buscado.setDatosReporte(Reporte.getDatosReporte());
            buscado.setFechaReporte(Reporte.getFechaReporte());
            buscado.setDescripcionReporte(Reporte.getDescripcionReporte());
            buscado.setTipoReporte(Reporte.getTipoReporte());

            repository.save(buscado);
            return " actualizado con exito";
        } else {
            return "No se encuentra ";
        }

    }

    public Reporte getReporte(int id) {
        String output = "";
        if (repository.existsById(id)) {
            Reporte reporte = repository.findById(id).get();
            output = reporte.toString();
            return reporte;
        } else {
            return null;
        }
    }

    public List<Reporte> getAllReportes() {
        return repository.findAll();
    }
}

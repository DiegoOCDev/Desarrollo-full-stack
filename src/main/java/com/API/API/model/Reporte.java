package com.API.API.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "reporte")
public class Reporte {
    @Id
    @Column(name = "idReporte", nullable = false)
    @JsonIgnore


    private Integer id;

    @Column(name = "tipoReporte", nullable = false, length = 100)
    private String tipoReporte;

    @Column(name = "datosReporte", nullable = false)
    private String datosReporte;

    @Column(name = "fechaReporte")
    private LocalDate fechaReporte;

    @Column(name = "descripcionReporte")
    private String descripcionReporte;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "idSoporte", nullable = false)
    private Soporte idSoporte;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "idReportante", nullable = false)
    private Usuario idReportante;

}
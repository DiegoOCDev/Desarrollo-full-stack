package com.API.API.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "soporte")
public class Soporte {
    @Id
    @Column(name = "idSoporte", nullable = false)
    private Integer idSoporte;

    @Column(name = "correoSoporte", nullable = false, length = 100)
    private String correoSoporte;

    @Column(name = "nombreSoporte", nullable = false, length = 100)
    private String nombreSoporte;

}
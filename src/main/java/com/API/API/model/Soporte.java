package com.API.API.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
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
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Soporte {
    @Id
    @Column(name = "idSoporte", nullable = false)
    @JsonIgnore

    private Integer id;

    @Column(name = "correoSoporte", nullable = false, length = 100)
    private String correoSoporte;

    @Column(name = "nombreSoporte", nullable = false, length = 100)
    private String nombreSoporte;

}
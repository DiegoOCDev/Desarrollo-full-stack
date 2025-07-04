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
@Table(name = "gerente")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})

public class Gerente {
    @Id
    @Column(name = "idGerente", nullable = false)
    @JsonIgnore


    private Integer id;

    @Column(name = "nombreGerente", nullable = false, length = 100)
    private String nombreGerente;

    @Column(name = "correoGerente", nullable = false, length = 100)
    private String correoGerente;

}
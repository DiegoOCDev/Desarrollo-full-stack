package com.API.API.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "estudiante")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Estudiante {
    @Id
    @Column(name = "idEstudiante", nullable = false)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)

    private Integer id;

    @Column(name = "nombreEstudiante", nullable = false, length = 100)
    private String nombreEstudiante;

    @Column(name = "correoEstudiante", nullable = false, length = 100)
    private String correoEstudiante;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "idUsuario", nullable = false)
    private Usuario idUsuario;

}
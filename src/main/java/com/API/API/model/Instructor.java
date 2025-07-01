package com.API.API.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "instructor")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Instructor {
    @Id
    @Column(name = "idInstructor", nullable = false)
    @JsonIgnore


    private Integer id;

    @Column(name = "nombreInstructor", nullable = false, length = 100)
    private String nombreInstructor;

    @Column(name = "correoInstructor", nullable = false, length = 100)
    private String correoInstructor;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "idUsuario", nullable = false)
    private Usuario idUsuario;

}
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
@Table(name = "instructor")
public class Instructor {
    @Id
    @Column(name = "idInstructor", nullable = false)
    private Integer idInstructor;

    @Column(name = "nombreInstructor", nullable = false, length = 100)
    private String nombreInstructor;

    @Column(name = "correoInstructor", nullable = false, length = 100)
    private String correoInstructor;

}
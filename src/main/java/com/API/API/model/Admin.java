package com.API.API.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "admin")
public class Admin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idAdmin", nullable = false)
    private Integer idAdmin;

    @Column(name = "nombreAdmin", nullable = false, length = 100)
    private String nombreAdmin;

    @Column(name = "correoAdmin", nullable = false, length = 100)
    private String correoAdmin;

}
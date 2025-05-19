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
@Table(name = "admin")
public class Admin {
    @Id
    @Column(name = "idAdmin", nullable = false)
    private Integer id;

    @Column(name = "nombreAdmin", nullable = false, length = 100)
    private String nombreAdmin;

    @Column(name = "correoAdmin", nullable = false, length = 100)
    private String correoAdmin;

}
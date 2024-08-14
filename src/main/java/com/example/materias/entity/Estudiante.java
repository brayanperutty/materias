package com.example.materias.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@Builder
@Table(name = "estudiante")
@AllArgsConstructor
@NoArgsConstructor
public class Estudiante {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_estudiante")
    private Integer id;

    @Column(name = "codigo_estudiante")
    private String codigo;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "promedio")
    private Float promedio;

    @OneToMany(mappedBy = "estudiante", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<Nota> notas = new HashSet<>();
}

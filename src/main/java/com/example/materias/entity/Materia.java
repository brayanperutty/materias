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
@Table(name = "materia", uniqueConstraints = {@UniqueConstraint(columnNames = {"codigo_materia"})})
@AllArgsConstructor
@NoArgsConstructor
public class Materia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_materia", nullable = false)
    private Integer id;

    @Column(name = "codigo_materia", nullable = false)
    private String codigo;

    @Column(name = "nombre", nullable = false)
    private String nombre;

    @OneToMany(mappedBy = "materia", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<Nota> notas = new HashSet<>();
}

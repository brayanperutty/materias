package com.example.materias.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "nota")
public class Nota {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "primera_nota")
    private float primeraNota;

    @Column(name = "segunda_nota")
    private float segundaNota;

    @Column(name = "tercera_nota")
    private float terceraNota;

    @Column(name = "nota_definitiva")
    private float definitiva;

    @ManyToOne
    @JoinColumn(name = "id_estudiante", nullable = false)
    @JsonIgnore
    private Estudiante estudiante;

    @ManyToOne
    @JoinColumn(name = "id_materia", nullable = false)
    @JsonIgnore
    private Materia materia;


}

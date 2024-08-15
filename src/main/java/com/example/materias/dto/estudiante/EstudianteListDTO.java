package com.example.materias.dto.estudiante;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class EstudianteListDTO {

    private String codigo;
    private String nombre;
    private float promedio;
}

package com.example.materias.responses.estudiante;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class EstudianteCreateResponses {

    private String message = "Estudiante creado con éxito";
}

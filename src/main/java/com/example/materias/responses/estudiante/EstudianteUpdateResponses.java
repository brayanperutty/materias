package com.example.materias.responses.estudiante;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class EstudianteUpdateResponses {

    private String message = "Estudiante actualizado con éxito";
}

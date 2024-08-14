package com.example.materias.responses.estudiante;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class EstudianteErrorResponses {

    private String noEncontrado = "Estudiante no encontrado";

    private String existente = "Código de estudiante ya se encuentra registrado en la plataforma";
}

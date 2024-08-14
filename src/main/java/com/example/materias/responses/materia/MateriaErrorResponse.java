package com.example.materias.responses.materia;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class MateriaErrorResponse {

    private String noEncontrada = "Materia no encontrada";
    private String existente = "Código de materia ya se encuentra registrado en la plataforma";
}

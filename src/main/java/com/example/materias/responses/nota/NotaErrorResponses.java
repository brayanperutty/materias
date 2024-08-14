package com.example.materias.responses.nota;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class NotaErrorResponses {

    private String noLectura = "Error: no se pudo leer el archivo excel";
    private String noEncontrada = "Estudiante no corresponde a esta materia";
}

package com.example.materias.responses.nota;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class NotaSuccessfulResponse {

    private String message = "Notas cargadas éxitosamente en la plataforma";
}

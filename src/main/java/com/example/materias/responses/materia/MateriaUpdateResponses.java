package com.example.materias.responses.materia;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class MateriaUpdateResponses {

    private String message = "Materia actualizada con éxito";
}

package com.example.materias.responses.materia;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class MateriaCreateResponse {

    private String message = "Materia creada con éxito";
}

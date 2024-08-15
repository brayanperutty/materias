package com.example.materias.dto.nota;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class NotaPromedioDTO {

    private String nombre;
    private float promedio;
}

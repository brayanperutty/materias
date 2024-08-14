package com.example.materias.dto.nota;


import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class NotaResponseDTO {

    private String nombre;
    private String codigo;
    private Float primeraNota;
    private Float segundaNota;
    private Float terceraNota;
    private Float definitiva;
}

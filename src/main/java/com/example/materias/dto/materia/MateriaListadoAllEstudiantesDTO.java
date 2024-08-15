package com.example.materias.dto.materia;

import com.example.materias.dto.nota.NotaResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Data
@Component
@NoArgsConstructor
@AllArgsConstructor
public class MateriaListadoAllEstudiantesDTO {

    private String materia;
    private List<NotaResponseDTO> estudiantes;
}

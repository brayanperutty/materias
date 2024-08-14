package com.example.materias.controller;

import com.example.materias.dto.nota.NotaResponseDTO;
import com.example.materias.service.NotaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/notas")
public class NotaController {

    private final NotaService notaService;

    @GetMapping(value = "/{id}/estudiantes")
    @ResponseStatus(HttpStatus.OK)
    public List<NotaResponseDTO> listEstudiantesNotasByMateria(@PathVariable Integer id){
        return notaService.listEstudianteByMateria(id);
    }

}

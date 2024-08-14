package com.example.materias.controller;


import com.example.materias.dto.estudiante.EstudianteCreateDTO;
import com.example.materias.dto.estudiante.EstudianteUpdateDTO;
import com.example.materias.entity.Estudiante;
import com.example.materias.responses.estudiante.EstudianteCreateResponses;
import com.example.materias.responses.estudiante.EstudianteUpdateResponses;
import com.example.materias.service.EstudianteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/estudiantes")
public class EstudianteController {

    private final EstudianteService estudianteService;

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Estudiante getEstudiante(@PathVariable Integer id){
        return estudianteService.getEstudiante(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EstudianteCreateResponses createEstudiante(@RequestBody EstudianteCreateDTO estudianteCreateDTO){
        return estudianteService.createEstudiante(estudianteCreateDTO);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public EstudianteUpdateResponses updateEstudiante(@RequestBody EstudianteUpdateDTO estudianteUpdateDTO){
        return estudianteService.updateEstudiante(estudianteUpdateDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteEstudiante(@PathVariable Integer id){
        estudianteService.deleteEstudiante(id);
    }
}

package com.example.materias.controller;

import com.example.materias.dto.materia.MateriaCreateDTO;
import com.example.materias.dto.materia.MateriaUpdateDTO;
import com.example.materias.entity.Materia;
import com.example.materias.responses.materia.MateriaCreateResponse;
import com.example.materias.responses.materia.MateriaUpdateResponses;
import com.example.materias.service.MateriaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/materias")
public class MateriaController {

    private final MateriaService materiaService;

    @GetMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Materia getMateria(@PathVariable Integer id){
        return materiaService.getMateria(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MateriaCreateResponse createMateria(@RequestBody MateriaCreateDTO materiaCreateDTO) {
        return materiaService.createMateria(materiaCreateDTO);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public MateriaUpdateResponses updateMateria(@RequestBody MateriaUpdateDTO materiaUpdateDTO){
        return materiaService.updateMateria(materiaUpdateDTO);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteMateria(@PathVariable Integer id){
        materiaService.deleteMateria(id);
    }
}

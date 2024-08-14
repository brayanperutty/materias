package com.example.materias.controller;

import com.example.materias.dto.materia.MateriaCreateDTO;
import com.example.materias.dto.materia.MateriaUpdateDTO;
import com.example.materias.entity.Materia;
import com.example.materias.responses.materia.MateriaCreateResponse;
import com.example.materias.responses.materia.MateriaUpdateResponses;
import com.example.materias.responses.nota.NotaSuccessfulResponse;
import com.example.materias.service.MateriaService;
import com.example.materias.util.ReadExcelService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/materias")
public class MateriaController {

    private final MateriaService materiaService;
    private final ReadExcelService readExcelService;

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

    @PostMapping(value = "/{id}/cargar-notas")
    @ResponseStatus(HttpStatus.OK)
    public NotaSuccessfulResponse cargarNotasMateria(@RequestParam("file") MultipartFile file, @PathVariable Integer id) throws IOException {
        return readExcelService.processExcelFile(file, id);
    }
}

package com.example.materias.controller;

import com.example.materias.service.NotaService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/notas")
public class NotaController {

    private final NotaService notaService;



}

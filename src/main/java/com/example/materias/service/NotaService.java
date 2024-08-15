package com.example.materias.service;

import com.example.materias.repository.NotaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotaService {

    private final NotaRepository notaRepository;
}

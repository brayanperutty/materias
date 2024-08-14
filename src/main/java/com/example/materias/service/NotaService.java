package com.example.materias.service;

import com.example.materias.dto.nota.NotaResponseDTO;
import com.example.materias.repository.NotaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NotaService {

    private final NotaRepository notaRepository;

    private List<NotaResponseDTO> notasEstudiantes = new ArrayList<>();

    public List<NotaResponseDTO> listEstudianteByMateria(Integer idMateria){
        notasEstudiantes.clear();

        notaRepository.listEstudiantesByMateria(idMateria)
                .forEach(nota -> {
                    NotaResponseDTO notaResponseDTO = new NotaResponseDTO();
                    notaResponseDTO.setNombre(nota.getNombre());
                    notaResponseDTO.setCodigo(nota.getCodigo());
                    notaResponseDTO.setPrimeraNota(nota.getPrimeraNota());
                    notaResponseDTO.setSegundaNota(nota.getSegundaNota());
                    notaResponseDTO.setTerceraNota(nota.getTerceraNota());
                    notaResponseDTO.setDefinitiva(nota.getDefinitiva());

                    notasEstudiantes.add(notaResponseDTO);
                });

        return notasEstudiantes;
    }
}

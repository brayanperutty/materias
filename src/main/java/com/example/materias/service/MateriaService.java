package com.example.materias.service;

import com.example.materias.dto.materia.MateriaCreateDTO;
import com.example.materias.dto.materia.MateriaUpdateDTO;
import com.example.materias.dto.nota.NotaResponseDTO;
import com.example.materias.entity.Materia;
import com.example.materias.repository.MateriaRepository;
import com.example.materias.responses.materia.MateriaCreateResponse;
import com.example.materias.responses.materia.MateriaErrorResponse;
import com.example.materias.responses.materia.MateriaUpdateResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MateriaService {

    private final MateriaRepository materiaRepository;
    private final MateriaErrorResponse materiaErrorResponse;
    private final MateriaCreateResponse materiaCreateResponse;
    private final MateriaUpdateResponses materiaUpdateResponses;

    private List<NotaResponseDTO> notasEstudiantes = new ArrayList<>();

    public Materia getMateria(Integer id){
        return materiaRepository.findById(id).orElseThrow(() -> new IllegalArgumentException(materiaErrorResponse.getNoEncontrada()));
    }

    public MateriaCreateResponse createMateria(MateriaCreateDTO materiaCreateDTO){
        if(!materiaRepository.validateMateria(materiaCreateDTO.getCodigo())){
            Materia materia = new Materia();
            materia.setCodigo(materiaCreateDTO.getCodigo());
            materia.setNombre(materiaCreateDTO.getNombre());

            materiaRepository.save(materia);
            return materiaCreateResponse;
        }else{
            throw new IllegalArgumentException(materiaErrorResponse.getExistente());
        }
    }

    public MateriaUpdateResponses updateMateria(MateriaUpdateDTO materiaUpdateDTO){
        if(materiaRepository.existsById(materiaUpdateDTO.getId())){
            Materia materia = new Materia();
            materia.setCodigo(materiaUpdateDTO.getCodigo());
            materia.setNombre(materiaUpdateDTO.getNombre());

            materiaRepository.save(materia);
            return materiaUpdateResponses;
        }else{
            throw new IllegalArgumentException(materiaErrorResponse.getNoEncontrada());
        }
    }

    public void deleteMateria(Integer id){
        if(materiaRepository.existsById(id)){
            materiaRepository.deleteById(id);
        }else{
            throw new IllegalArgumentException(materiaErrorResponse.getNoEncontrada());
        }
    }

    public List<NotaResponseDTO> listEstudianteByMateria(Integer idMateria){
        notasEstudiantes.clear();

        materiaRepository.listEstudiantesByMateria(idMateria)
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

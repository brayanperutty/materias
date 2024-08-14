package com.example.materias.service;

import com.example.materias.dto.estudiante.EstudianteCreateDTO;
import com.example.materias.dto.estudiante.EstudianteUpdateDTO;
import com.example.materias.entity.Estudiante;
import com.example.materias.repository.EstudianteRepository;
import com.example.materias.responses.estudiante.EstudianteCreateResponses;
import com.example.materias.responses.estudiante.EstudianteErrorResponses;
import com.example.materias.responses.estudiante.EstudianteUpdateResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EstudianteService {

    private final EstudianteRepository estudianteRepository;
    private final EstudianteErrorResponses estudianteErrorResponses;
    private final EstudianteCreateResponses estudianteCreateResponses;
    private final EstudianteUpdateResponses estudianteUpdateResponses;

    public Estudiante getEstudiante(Integer id){
        return estudianteRepository.findById(id).orElseThrow(() -> new IllegalArgumentException(estudianteErrorResponses.getNoEncontrado()));
    }

    public EstudianteCreateResponses createEstudiante(EstudianteCreateDTO estudianteCreateDTO){
        if(!estudianteRepository.validateEstudiante(estudianteCreateDTO.getCodigo())){

            Estudiante estudiante = new Estudiante();
            estudiante.setCodigo(estudiante.getCodigo());
            estudiante.setNombre(estudiante.getNombre());

            estudianteRepository.save(estudiante);
            return estudianteCreateResponses;
        }else{
            throw new IllegalArgumentException(estudianteErrorResponses.getExistente());
        }
    }

    public EstudianteUpdateResponses updateEstudiante(EstudianteUpdateDTO estudianteUpdateDTO){
        if(estudianteRepository.existsById(estudianteUpdateDTO.getId())){
            Estudiante estudiante = new Estudiante();
            estudiante.setCodigo(estudiante.getCodigo());
            estudiante.setNombre(estudiante.getNombre());

            estudianteRepository.save(estudiante);
            return estudianteUpdateResponses;
        }else{
            throw new IllegalArgumentException(estudianteErrorResponses.getNoEncontrado());
        }
    }

    public void deleteEstudiante(Integer id){
        if(estudianteRepository.existsById(id)){
            estudianteRepository.deleteById(id);
        }else{
            throw new IllegalArgumentException(estudianteErrorResponses.getNoEncontrado());
        }
    }
}

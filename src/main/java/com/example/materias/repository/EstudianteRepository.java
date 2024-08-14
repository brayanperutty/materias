package com.example.materias.repository;

import com.example.materias.entity.Estudiante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface EstudianteRepository extends JpaRepository<Estudiante, Integer> {

    @Query(value = "SELECT CASE WHEN COUNT(e) > 0 THEN TRUE ELSE FALSE END FROM estudiante e WHERE e.codigo = :codigo", nativeQuery = true)
    boolean validateEstudiante(String codigo);
}

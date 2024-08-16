package com.example.materias.repository;

import com.example.materias.dto.projection.PromedioProjection;
import com.example.materias.entity.Estudiante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EstudianteRepository extends JpaRepository<Estudiante, Integer> {

    @Query(value = "SELECT CASE WHEN COUNT(e) > 0 THEN TRUE ELSE FALSE END FROM estudiante e WHERE e.codigo = :codigo", nativeQuery = true)
    boolean validateEstudiante(String codigo);

    Optional<Estudiante> findByCodigo(String codigo);

    @Query(value = "SELECT e.nombre as nombre, CAST(AVG(e.promedio) as real) as promedio FROM estudiante e " +
            "GROUP BY e.nombre " +
            "ORDER BY promedio DESC", nativeQuery = true)
    List<PromedioProjection> listAllPromedio();
}

package com.example.materias.repository;

import com.example.materias.dto.projection.NotasProjection;
import com.example.materias.entity.Materia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MateriaRepository extends JpaRepository<Materia, Integer> {

    @Query(value = "SELECT CASE WHEN COUNT(m) > 0 THEN TRUE ELSE FALSE END FROM materia m WHERE m.codigo = :codigo", nativeQuery = true)
    boolean validateMateria(String codigo);

    @Query(value = "SELECT e.nombre as nombre, e.codigo_estudiante as codigo, n.primera_nota as primeraNota, n.segunda_nota as segundaNota, n.tercera_nota as terceraNota, n.nota_definitiva as definitiva " +
            "FROM nota n " +
            "JOIN materia m ON m.id_materia = n.id_materia " +
            "JOIN estudiante e ON e.id_estudiante = n.id_estudiante " +
            "WHERE n.id_materia = :idMateria " +
            "GROUP BY e.nombre, e.codigo_estudiante, n.nota_definitiva, n.id_estudiante " +
            "ORDER BY n.nota_definitiva DESC", nativeQuery = true)
    List<NotasProjection> listEstudiantesByMateria(Integer idMateria);
}

package com.example.materias.repository;

import com.example.materias.dto.projection.NotasProjection;
import com.example.materias.entity.Estudiante;
import com.example.materias.entity.Materia;
import com.example.materias.entity.Nota;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NotaRepository extends JpaRepository<Nota, Integer> {

    @Query(value = "SELECT CASE WHEN COUNT(n) > 0 THEN TRUE ELSE FALSE END FROM nota n " +
            "JOIN estudiante e ON e.id_estudiante = n.id_estudiante " +
            "JOIN materia m ON m.id_materia = n.id_materia " +
            "WHERE n.id_materia = :idMateria AND n.id_estudiante = :idEstudiante", nativeQuery = true)
    boolean validateEstudianteMateria(Integer idMateria, Integer idEstudiante);

    Optional<Nota> findByEstudianteAndMateria(Estudiante estudiante, Materia materia);

    @Query(value = "SELECT e.nombre as nombre, e.codigo_estudiante as codigo, n.primera_nota as primeraNota, n.segunda_nota as segundaNota, " +
            "n.tercera_nota as terceraNota, n.nota_definitiva as definitiva " +
            "FROM nota n " +
            "JOIN materia m ON m.id_materia = n.id_materia " +
            "JOIN estudiante e ON e.id_estudiante = n.id_estudiante " +
            "WHERE n.id_materia = :idMateria " +
            "GROUP BY e.nombre, e.codigo_estudiante, n.primera_nota, n.segunda_nota, n.tercera_nota, n.nota_definitiva, n.id_estudiante " +
            "ORDER BY n.id_estudiante", nativeQuery = true)
    List<NotasProjection> listEstudiantesByMateria(Integer idMateria);
}

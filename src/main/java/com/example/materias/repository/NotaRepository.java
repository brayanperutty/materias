package com.example.materias.repository;

import com.example.materias.entity.Estudiante;
import com.example.materias.entity.Materia;
import com.example.materias.entity.Nota;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface NotaRepository extends JpaRepository<Nota, Integer> {

    @Query(value = "SELECT CASE WHEN COUNT(n) > 0 THEN TRUE ELSE FALSE END FROM nota n " +
            "JOIN estudiante e ON e.id_estudiante = n.id_estudiante " +
            "JOIN materia m ON m.id_materia = n.id_materia " +
            "WHERE n.id_materia = :idMateria AND n.id_estudiante = :idEstudiante", nativeQuery = true)
    boolean validateEstudianteMateria(Integer idMateria, Integer idEstudiante);

    Optional<Nota> findByEstudianteAndMateria(Estudiante estudiante, Materia materia);

    @Query(value = "SELECT CAST(AVG(n.nota_definitiva) as real) as promedio FROM nota n " +
            "JOIN estudiante e ON e.id_estudiante = n.id_estudiante " +
            "JOIN materia m ON m.id_materia = n.id_materia " +
            "WHERE n.id_estudiante = :idEstudiante", nativeQuery = true)
    float calcularPromedio(Integer idEstudiante);
}

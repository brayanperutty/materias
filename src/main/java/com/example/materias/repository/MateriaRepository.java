package com.example.materias.repository;

import com.example.materias.entity.Materia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface MateriaRepository extends JpaRepository<Materia, Integer> {

    @Query(value = "SELECT CASE WHEN COUNT(m) > 0 THEN TRUE ELSE FALSE END FROM materia m WHERE m.codigo = :codigo", nativeQuery = true)
    boolean validateMateria(String codigo);
}

package com.example.materias.util;

import com.example.materias.entity.Estudiante;
import com.example.materias.entity.Materia;
import com.example.materias.entity.Nota;
import com.example.materias.repository.EstudianteRepository;
import com.example.materias.repository.MateriaRepository;
import com.example.materias.repository.NotaRepository;
import com.example.materias.responses.estudiante.EstudianteErrorResponses;
import com.example.materias.responses.materia.MateriaErrorResponse;
import com.example.materias.responses.nota.NotaErrorResponses;
import com.example.materias.responses.nota.NotaSuccessfulResponse;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Iterator;

@Service
@RequiredArgsConstructor
public class ReadExcelService {

    private final EstudianteRepository estudianteRepository;
    private final MateriaRepository materiaRepository;
    private final NotaRepository notaRepository;
    private final EstudianteErrorResponses estudianteErrorResponses;
    private final MateriaErrorResponse materiaErrorResponse;
    private final NotaSuccessfulResponse notaSuccessfulResponse;
    private final NotaErrorResponses notaErrorResponses;

    public NotaSuccessfulResponse processExcelFile(MultipartFile file, Integer idMateria)throws IOException {


        try (Workbook workbook = WorkbookFactory.create(file.getInputStream())){
            Sheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rowIterator = sheet.iterator();

            if (rowIterator.hasNext()) {
                rowIterator.next();
            }

            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();

                if (row.getPhysicalNumberOfCells() == 0) {
                    // Si la fila está vacía, detén el bucle while
                    break;
                }

                Iterator<Cell> cellIterator = row.cellIterator();

                String codigoEstudiante = cellIterator.next().getStringCellValue();
                Estudiante estudiante = estudianteRepository.findByCodigo(codigoEstudiante).orElseThrow(() -> new IllegalArgumentException(estudianteErrorResponses.getNoEncontrado()));

                Materia materia = materiaRepository.findById(idMateria).orElseThrow(() -> new IllegalArgumentException(materiaErrorResponse.getNoEncontrada()));

                String primeraNota = cellIterator.next().getStringCellValue();
                String segundaNota = cellIterator.next().getStringCellValue();
                String terceraNota = cellIterator.next().getStringCellValue();

                float definitiva = (Float.parseFloat(primeraNota) + Float.parseFloat(segundaNota) + Float.parseFloat(terceraNota))/3;
                definitiva = Math.round(definitiva * 10.0f) / 10.0f;
                Nota nota = notaRepository.findByEstudianteAndMateria(estudiante, materia).orElseThrow(() -> new IllegalArgumentException("El estudiante " + estudiante.getNombre() + " no corresponde a la materia " + materia.getNombre() +". Verifique los demás estudiantes ingresados."));
                nota.setEstudiante(estudiante);
                nota.setMateria(materia);
                nota.setPrimeraNota(Float.parseFloat(primeraNota));
                nota.setSegundaNota(Float.parseFloat(segundaNota));
                nota.setTerceraNota(Float.parseFloat(terceraNota));
                nota.setDefinitiva(definitiva);

                notaRepository.save(nota);
            }
            return notaSuccessfulResponse;
        }catch (IOException e){
            throw new IOException(notaErrorResponses.getNoLectura());
        }
    }
}

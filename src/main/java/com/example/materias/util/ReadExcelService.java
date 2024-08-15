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
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

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

    private final List<String> estudiantesNoCorrespondientes = new ArrayList<>();

    public NotaSuccessfulResponse processExcelFile(MultipartFile file, Integer idMateria)throws IOException {
        estudiantesNoCorrespondientes.clear();

        NotaSuccessfulResponse respuestaTemporal = new NotaSuccessfulResponse();

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

                Optional<Nota> nota = notaRepository.findByEstudianteAndMateria(estudiante, materia);

                if(nota.isEmpty()){
                    estudiantesNoCorrespondientes.add(estudiante.getNombre());
                }else{

                    Cell primeraNotaCell = cellIterator.next();
                    float primeraNota = getFloatValueFromCell(primeraNotaCell);

                    Cell segundaNotaCell = cellIterator.next();
                    float segundaNota = getFloatValueFromCell(segundaNotaCell);

                    Cell terceraNotaCell = cellIterator.next();
                    float tercerNota = getFloatValueFromCell(terceraNotaCell);

                    float definitiva = (primeraNota + segundaNota + tercerNota)/3;

                    estudiante.setPromedio(notaRepository.calcularPromedio(estudiante.getId()));
                    estudianteRepository.save(estudiante);

                    definitiva = Math.round(definitiva * 10.0f) / 10.0f;

                    nota.get().setEstudiante(estudiante);
                    nota.get().setMateria(materia);
                    nota.get().setPrimeraNota(primeraNota);
                    nota.get().setSegundaNota(segundaNota);
                    nota.get().setTerceraNota(tercerNota);
                    nota.get().setDefinitiva(definitiva);

                    notaRepository.save(nota.get());
                }
            }

            if(!estudiantesNoCorrespondientes.isEmpty()){
                respuestaTemporal.setMessage(notaSuccessfulResponse.getMessage() + ". Estos estudiantes no pertenecen a la asignatura: " + estudiantesNoCorrespondientes);
            }
            return respuestaTemporal;
        }catch (IOException e){
            throw new IOException(notaErrorResponses.getNoLectura());
        }
    }

    private float getFloatValueFromCell(Cell cell) {
        try {
            return switch (cell.getCellType()) {
                case NUMERIC -> (float) cell.getNumericCellValue();
                case STRING -> Float.parseFloat(cell.getStringCellValue());
                default ->
                        throw new IllegalArgumentException("El valor de la celda no es numérico ni un string convertible a número.");
            };
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("El valor de la celda no se puede convertir a float: " + cell.getStringCellValue(), e);
        }
    }
}

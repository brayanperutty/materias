package com.example.materias.controller;

import com.example.materias.dto.materia.MateriaCreateDTO;
import com.example.materias.dto.materia.MateriaListadoAllEstudiantesDTO;
import com.example.materias.dto.materia.MateriaUpdateDTO;
import com.example.materias.dto.nota.NotaResponseDTO;
import com.example.materias.entity.Materia;
import com.example.materias.repository.MateriaRepository;
import com.example.materias.responses.materia.MateriaCreateResponse;
import com.example.materias.responses.materia.MateriaErrorResponse;
import com.example.materias.responses.materia.MateriaUpdateResponses;
import com.example.materias.responses.nota.NotaSuccessfulResponse;
import com.example.materias.service.MateriaService;
import com.example.materias.util.ExportPDF;
import com.example.materias.util.ReadExcelService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/materias")
public class MateriaController {

    private final MateriaService materiaService;
    private final ReadExcelService readExcelService;
    private final MateriaRepository materiaRepository;
    private final MateriaErrorResponse materiaErrorResponse;

    @GetMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Materia getMateria(@PathVariable Integer id){
        return materiaService.getMateria(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MateriaCreateResponse createMateria(@RequestBody MateriaCreateDTO materiaCreateDTO) {
        return materiaService.createMateria(materiaCreateDTO);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public MateriaUpdateResponses updateMateria(@RequestBody MateriaUpdateDTO materiaUpdateDTO){
        return materiaService.updateMateria(materiaUpdateDTO);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteMateria(@PathVariable Integer id){
        materiaService.deleteMateria(id);
    }

    @PostMapping(value = "/{id}/cargar-notas")
    @ResponseStatus(HttpStatus.OK)
    public NotaSuccessfulResponse cargarNotasMateria(@RequestParam("file") MultipartFile file, @PathVariable Integer id) throws IOException {
        return readExcelService.processExcelFile(file, id);
    }

    @GetMapping(value = "/{id}/estudiantes")
    @ResponseStatus(HttpStatus.OK)
    public List<NotaResponseDTO> listEstudiantesNotasByMateria(@PathVariable Integer id){
        return materiaService.listEstudianteByMateria(id);
    }

    @GetMapping("/{id}/pdf")
    @ResponseStatus(HttpStatus.OK)
    public void downloadFile(HttpServletResponse response, @PathVariable Integer id) throws IOException {
        ExportPDF generator = new ExportPDF(materiaRepository, materiaErrorResponse);
        byte[] pdfReport = generator.getPDF(id).toByteArray();

        String mimeType =  "application/pdf";
        response.setContentType(mimeType);
        response.setHeader("Content-Disposition", String.format("attachment; filename=\"%s\"", "reporte-notas.pdf"));

        response.setContentLength(pdfReport.length);

        ByteArrayInputStream inStream = new ByteArrayInputStream( pdfReport);

        FileCopyUtils.copy(inStream, response.getOutputStream());
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Materia> listMaterias(){
        return materiaService.listMaterias();
    }

    @GetMapping("/estudiantes")
    @ResponseStatus(HttpStatus.OK)
    public List<MateriaListadoAllEstudiantesDTO> listadoAllEstudiantesDTO(){
        return materiaService.listEstudiantesAllMaterias();
    }
}

package com.example.materias.util;

import com.example.materias.entity.Materia;
import com.example.materias.repository.MateriaRepository;
import com.example.materias.responses.materia.MateriaErrorResponse;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.ByteArrayOutputStream;

public class ExportPDF {

    private final MateriaRepository materiaRepository;
    private final MateriaErrorResponse materiaErrorResponse;

    public ExportPDF(MateriaRepository materiaRepository, MateriaErrorResponse materiaErrorResponse) {

        this.materiaRepository = materiaRepository;
        this.materiaErrorResponse = materiaErrorResponse;
    }

    public ByteArrayOutputStream getPDF(Integer idMateria) {

        Materia materia = materiaRepository.findById(idMateria).orElseThrow(() -> new IllegalArgumentException(materiaErrorResponse.getNoEncontrada()));

        // Creamos la instancia de memoria en la que se escribirá el archivo temporalmente
        try (ByteArrayOutputStream bos = new ByteArrayOutputStream()) {

            Document document = new Document(PageSize.A4);

            Font fuenteTitulo = new Font();
            fuenteTitulo.setSize(10);
            fuenteTitulo.setStyle(Font.BOLD);

            Font fuenteColumnas = new Font();
            fuenteColumnas.setSize(9);
            fuenteColumnas.setStyle(Font.BOLD);

            Font fuenteNotas = new Font();
            fuenteNotas.setSize(9);

            Chunk codigo = new Chunk("COD", fuenteColumnas);

            Chunk nombre = new Chunk("NOMBRE", fuenteColumnas);

            Chunk primeraNota = new Chunk("1RA", fuenteColumnas);

            Chunk segundaNota = new Chunk("2DA", fuenteColumnas);

            Chunk terceraNota = new Chunk("3RA", fuenteColumnas);

            Chunk definitiva = new Chunk("DEFINITIVA", fuenteColumnas);


            Chunk parrafo = new Chunk("LISTADO DE ESTUDIANTES DE " + materia.getNombre() + " POR DEFINITIVA DE MAYOR A MENOR", fuenteTitulo);

            PdfPTable tabla1 = new PdfPTable(1);
            PdfPTable tabla2 = new PdfPTable(6);

            PdfPCell celda1 = new PdfPCell(new Phrase(parrafo));
            PdfPCell celda2 = new PdfPCell(new Phrase(codigo));
            PdfPCell celda3 = new PdfPCell(new Phrase(nombre));
            PdfPCell celda4 = new PdfPCell(new Phrase(primeraNota));
            PdfPCell celda5 = new PdfPCell(new Phrase(segundaNota));
            PdfPCell celda6 = new PdfPCell(new Phrase(terceraNota));
            PdfPCell celda7 = new PdfPCell(new Phrase(definitiva));

            celda1.setHorizontalAlignment(Element.ALIGN_CENTER);
            celda1.setVerticalAlignment(Element.ALIGN_MIDDLE);
            celda1.setFixedHeight(25f);

            celda2.setHorizontalAlignment(Element.ALIGN_CENTER);
            celda2.setVerticalAlignment(Element.ALIGN_MIDDLE);
            celda2.setFixedHeight(20f);

            celda3.setHorizontalAlignment(Element.ALIGN_CENTER);
            celda3.setVerticalAlignment(Element.ALIGN_MIDDLE);
            celda3.setFixedHeight(20f);

            celda4.setHorizontalAlignment(Element.ALIGN_CENTER);
            celda4.setVerticalAlignment(Element.ALIGN_MIDDLE);
            celda4.setFixedHeight(20f);

            celda5.setHorizontalAlignment(Element.ALIGN_CENTER);
            celda5.setVerticalAlignment(Element.ALIGN_MIDDLE);
            celda5.setFixedHeight(20f);

            celda6.setHorizontalAlignment(Element.ALIGN_CENTER);
            celda6.setVerticalAlignment(Element.ALIGN_MIDDLE);
            celda6.setFixedHeight(20f);

            celda7.setHorizontalAlignment(Element.ALIGN_CENTER);
            celda7.setVerticalAlignment(Element.ALIGN_MIDDLE);
            celda7.setFixedHeight(20f);

            float[] columnWidths = {1f, 8f, 1f, 1f, 1f, 2f};
            tabla2.setWidths(columnWidths);

            tabla1.addCell(celda1);
            tabla2.addCell(celda2);
            tabla2.addCell(celda3);
            tabla2.addCell(celda4);
            tabla2.addCell(celda5);
            tabla2.addCell(celda6);
            tabla2.addCell(celda7);

            PdfWriter.getInstance(document, bos);
            document.open();
            document.add(tabla1);
            document.add(tabla2);

            materiaRepository.listEstudiantesByMateria(idMateria)
                    .forEach(nota -> {

                        PdfPTable tabla = new PdfPTable(6);
                        try {
                            tabla.setWidths(columnWidths);
                        } catch (DocumentException e) {
                            throw new IllegalArgumentException(e);
                        }

                        PdfPCell celda10 = new PdfPCell(new Phrase(nota.getCodigo(), fuenteNotas));
                        PdfPCell celda11 = new PdfPCell(new Phrase(nota.getNombre(), fuenteNotas));
                        PdfPCell celda12 = new PdfPCell(new Phrase(String.valueOf(nota.getPrimeraNota()), fuenteNotas));
                        PdfPCell celda13 = new PdfPCell(new Phrase(String.valueOf(nota.getSegundaNota()), fuenteNotas));
                        PdfPCell celda14 = new PdfPCell(new Phrase(String.valueOf(nota.getTerceraNota()), fuenteNotas));
                        PdfPCell celda15 = new PdfPCell(new Phrase(String.valueOf(nota.getDefinitiva()), fuenteNotas));

                        celda10.setHorizontalAlignment(Element.ALIGN_CENTER);
                        celda10.setVerticalAlignment(Element.ALIGN_MIDDLE);

                        celda11.setHorizontalAlignment(Element.ALIGN_CENTER);
                        celda11.setVerticalAlignment(Element.ALIGN_MIDDLE);

                        celda12.setHorizontalAlignment(Element.ALIGN_CENTER);
                        celda12.setVerticalAlignment(Element.ALIGN_MIDDLE);

                        celda13.setHorizontalAlignment(Element.ALIGN_CENTER);
                        celda13.setVerticalAlignment(Element.ALIGN_MIDDLE);

                        celda14.setHorizontalAlignment(Element.ALIGN_CENTER);
                        celda14.setVerticalAlignment(Element.ALIGN_MIDDLE);

                        celda15.setHorizontalAlignment(Element.ALIGN_CENTER);
                        celda15.setVerticalAlignment(Element.ALIGN_MIDDLE);

                        celda10.setFixedHeight(20f);
                        celda11.setFixedHeight(20f);
                        celda12.setFixedHeight(20f);
                        celda13.setFixedHeight(20f);
                        celda14.setFixedHeight(20f);
                        celda15.setFixedHeight(20f);

                        tabla.addCell(celda10);
                        tabla.addCell(celda11);
                        tabla.addCell(celda12);
                        tabla.addCell(celda13);
                        tabla.addCell(celda14);
                        tabla.addCell(celda15);

                        try {
                            document.add(tabla);
                        } catch (DocumentException e) {
                            e.printStackTrace();
                        }
                    });

            document.close();
            // Retornamos la variable al finalizar
            return bos;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}

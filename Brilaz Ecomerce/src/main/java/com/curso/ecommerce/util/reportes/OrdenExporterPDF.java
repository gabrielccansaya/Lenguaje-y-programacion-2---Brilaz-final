package com.curso.ecommerce.util.reportes;

import com.curso.ecommerce.model.Orden;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import java.awt.Color;
import java.io.IOException;
import java.util.List;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author admin
 */
public class OrdenExporterPDF {

    private List<Orden> listaLibros;

    public OrdenExporterPDF(List<Orden> listaLibros) {
        super();
        this.listaLibros = listaLibros;
    }

    private void escribirCabeceraDeLaTabla(PdfPTable tabla) {
        PdfPCell celda = new PdfPCell();

        celda.setBackgroundColor(Color.BLUE);
        celda.setPadding(5);

        Font fuente = FontFactory.getFont(FontFactory.HELVETICA);
        fuente.setColor(Color.WHITE);

        celda.setPhrase(new Phrase("No. de Orden", fuente));
        tabla.addCell(celda);

        celda.setPhrase(new Phrase("Fecha", fuente));
        tabla.addCell(celda);

        celda.setPhrase(new Phrase("Valor", fuente));
        tabla.addCell(celda);

        celda.setPhrase(new Phrase("Detalle", fuente));
        tabla.addCell(celda);

    }

    private void escribirDatosDeLaTabla(PdfPTable tabla) {
        for (Orden libro : listaLibros) {
            tabla.addCell(String.valueOf(libro.getNumero()));
            tabla.addCell(String.valueOf(libro.getFechaCreacion()));
            tabla.addCell(String.valueOf(libro.getTotal()));
            tabla.addCell(String.valueOf(libro.getDetalle()));
        }
    }

    public void exportar(HttpServletResponse response) throws DocumentException, IOException {
        Document documento = new Document(PageSize.A4);
        PdfWriter.getInstance(documento, response.getOutputStream());

        documento.open();

        Font fuente = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        fuente.setColor(Color.BLUE);
        fuente.setSize(18);

        Paragraph titulo = new Paragraph("Lista de Ordenes", fuente);
        titulo.setAlignment(Paragraph.ALIGN_CENTER);
        documento.add(titulo);

        PdfPTable tabla = new PdfPTable(4);
        tabla.setWidthPercentage(100);
        tabla.setSpacingBefore(15);
        tabla.setWidths(new float[]{2.5f, 2.7f, 1.8f, 12f});
        tabla.setWidthPercentage(110);

        escribirCabeceraDeLaTabla(tabla);
        escribirDatosDeLaTabla(tabla);

        documento.add(tabla);
        documento.close();
    }
}

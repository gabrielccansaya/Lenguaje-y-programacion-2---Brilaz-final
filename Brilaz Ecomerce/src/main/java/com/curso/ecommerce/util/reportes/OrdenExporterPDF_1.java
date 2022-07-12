package com.curso.ecommerce.util.reportes;

import com.curso.ecommerce.model.Calzado;
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
public class OrdenExporterPDF_1 {

    private List<Calzado> listaLibros;

    public OrdenExporterPDF_1(List<Calzado> listaLibros) {
        super();
        this.listaLibros = listaLibros;
    }

    private void escribirCabeceraDeLaTabla(PdfPTable tabla) {
        PdfPCell celda = new PdfPCell();

        celda.setBackgroundColor(Color.BLUE);
        celda.setPadding(5);

        Font fuente = FontFactory.getFont(FontFactory.HELVETICA);
        fuente.setColor(Color.WHITE);

        celda.setPhrase(new Phrase("Nombre", fuente));
        tabla.addCell(celda);

        celda.setPhrase(new Phrase("Descripción", fuente));
        tabla.addCell(celda);

        celda.setPhrase(new Phrase("Inventario", fuente));
        tabla.addCell(celda);

        celda.setPhrase(new Phrase("Precio", fuente));
        tabla.addCell(celda);

    }

    private void escribirDatosDeLaTabla(PdfPTable tabla) {
        for (Calzado libro : listaLibros) {
            tabla.addCell(String.valueOf(libro.getNombre()));
            tabla.addCell(String.valueOf(libro.getDescripcion()));
            tabla.addCell(String.valueOf(libro.getCantidad()));
            tabla.addCell(String.valueOf(libro.getPrecio()));
        }
    }

    public void exportar(HttpServletResponse response) throws DocumentException, IOException {
        Document documento = new Document(PageSize.A4);
        PdfWriter.getInstance(documento, response.getOutputStream());

        documento.open();

        Font fuente = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        fuente.setColor(Color.BLUE);
        fuente.setSize(18);

        Paragraph titulo = new Paragraph("Lista de Calzados diponibles", fuente);
        titulo.setAlignment(Paragraph.ALIGN_CENTER);
        documento.add(titulo);

        PdfPTable tabla = new PdfPTable(4);
        tabla.setWidthPercentage(100);
        tabla.setSpacingBefore(15);
        tabla.setWidths(new float[]{5f, 7.7f, 2.3f, 4f});
        tabla.setWidthPercentage(110);

        escribirCabeceraDeLaTabla(tabla);
        escribirDatosDeLaTabla(tabla);

        documento.add(tabla);
        documento.close();
    }
}

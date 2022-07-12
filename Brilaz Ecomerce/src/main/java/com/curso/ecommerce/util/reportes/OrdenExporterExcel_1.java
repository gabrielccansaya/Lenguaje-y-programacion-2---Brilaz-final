package com.curso.ecommerce.util.reportes;

import com.curso.ecommerce.model.Calzado;
import com.curso.ecommerce.model.Orden;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author admin
 */
public class OrdenExporterExcel_1 {

    private XSSFWorkbook libro;
    private XSSFSheet hoja;

    private List<Calzado> listaLibros;

    public OrdenExporterExcel_1(List<Calzado> listaLibros) {
        this.listaLibros = listaLibros;
        libro = new XSSFWorkbook();
        hoja = libro.createSheet("Libros");
    }

    private void escribirCabeceraDeTabla() {
        Row fila = hoja.createRow(0);

        CellStyle estilo = libro.createCellStyle();
        XSSFFont fuente = libro.createFont();
        fuente.setBold(true);
        fuente.setFontHeight(16);
        estilo.setFont(fuente);

        Cell celda = fila.createCell(0);
        celda.setCellValue("Nombre");
        celda.setCellStyle(estilo);

        celda = fila.createCell(1);
        celda.setCellValue("Descripción");
        celda.setCellStyle(estilo);

        celda = fila.createCell(2);
        celda.setCellValue("Cantidad");
        celda.setCellStyle(estilo);

        celda = fila.createCell(3);
        celda.setCellValue("Precio");
        celda.setCellStyle(estilo);

    }

    private void escribirDatosDeLaTabla() {
        int nueroFilas = 1;

        CellStyle estilo = libro.createCellStyle();
        XSSFFont fuente = libro.createFont();
        fuente.setFontHeight(14);
        estilo.setFont(fuente);

        for (Calzado lib : listaLibros) {
            Row fila = hoja.createRow(nueroFilas++);

            Cell celda = fila.createCell(0);
            celda.setCellValue(lib.getNombre());
            hoja.autoSizeColumn(0);
            celda.setCellStyle(estilo);

            celda = fila.createCell(1);
            celda.setCellValue(lib.getDescripcion());
            hoja.autoSizeColumn(1);
            celda.setCellStyle(estilo);

            celda = fila.createCell(2);
            celda.setCellValue(lib.getCantidad());
            hoja.autoSizeColumn(2);
            celda.setCellStyle(estilo);

            celda = fila.createCell(3);
            celda.setCellValue(lib.getPrecio());
            hoja.autoSizeColumn(3);
            celda.setCellStyle(estilo);


        }
    }

    public void exportar(HttpServletResponse response) throws IOException {
        escribirCabeceraDeTabla();
        escribirDatosDeLaTabla();

        ServletOutputStream outPutStream = response.getOutputStream();
        libro.write(outPutStream);

        libro.close();
        outPutStream.close();
    }
}

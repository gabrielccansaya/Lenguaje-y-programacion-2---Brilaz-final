package com.curso.ecommerce.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.curso.ecommerce.model.Orden;
import com.curso.ecommerce.model.Calzado;
import com.curso.ecommerce.model.Usuario;
import com.curso.ecommerce.service.IOrdenService;
import com.curso.ecommerce.service.IUsuarioService;
import com.curso.ecommerce.service.ProductoService;
import com.curso.ecommerce.util.reportes.OrdenExporterExcel;
import com.curso.ecommerce.util.reportes.OrdenExporterPDF;
import com.lowagie.text.DocumentException;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/administrador")
public class AdministradorController {

    private final Logger logger = LoggerFactory.getLogger(UsuarioController.class);

    @Autowired
    private ProductoService productoService;

    @Autowired
    private IUsuarioService usuarioService;

    @Autowired
    private IOrdenService ordensService;

    private Logger logg = LoggerFactory.getLogger(AdministradorController.class);

    @GetMapping("")
    public String home(Model model) {

        List<Calzado> productos = productoService.findAll();
        model.addAttribute("productos", productos);

        return "administrador/home";
    }

    @GetMapping("/usuarios")
    public String usuarios(Model model) {
        model.addAttribute("usuarios", usuarioService.findAll());
        return "administrador/usuarios";
    }

    @GetMapping("/ordenes")
    public String ordenes(Model model) {
        model.addAttribute("ordenes", ordensService.findAll());
        return "administrador/ordenes";
    }

    @GetMapping("/detalle/{id}")
    public String detalle(Model model, @PathVariable Integer id) {
        logg.info("Id de la orden {}", id);
        Orden orden = ordensService.findById(id).get();

        model.addAttribute("detalles", orden.getDetalle());

        return "administrador/detalleorden";
    }

    @GetMapping("exportarPDF")
    public void exportarListadoDeEmpleadosEnPDF(HttpServletResponse response) throws DocumentException, IOException {
        response.setContentType("application/pdf");

        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String fechaActual = dateFormatter.format(new Date());

        String cabecera = "Content-Disposition";
        String valor = "attachment; filename=Libros_" + fechaActual + ".pdf";

        response.setHeader(cabecera, valor);

        List<Orden> libros = ordensService.readAll();

        OrdenExporterPDF exporter = new OrdenExporterPDF(libros);
        exporter.exportar(response);
    }

    @GetMapping("exportarExcel")
    public void exportarListadoDeEmpleadosEnExcel(HttpServletResponse response) throws DocumentException, IOException {
        response.setContentType("application/octet-stream");

        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String fechaActual = dateFormatter.format(new Date());

        String cabecera = "Content-Disposition";
        String valor = "attachment; filename=Libro_" + fechaActual + ".xlsx";

        response.setHeader(cabecera, valor);

        List<Orden> libros = ordensService.readAll();

        OrdenExporterExcel exporter = new OrdenExporterExcel(libros);
        exporter.exportar(response);
    }

    @GetMapping("/g1")
    public String g1(Model model) {
        Map<String, Integer> surveyMap = new LinkedHashMap<>();
        List<Calzado> libros = productoService.findAll();
        int aa = libros.size();
        
        for (int i = 1; i < aa+1; i++) {
            Optional<Calzado> user = productoService.findById(i);
            String a = user.get().getNombre();
            int b = user.get().getCantidad();
            surveyMap.put(a, b);
           
        }
 model.addAttribute("surveyMap", surveyMap);
        return "administrador/grafico1";
    }

    @GetMapping("/g2")
    public String g2(Model model) {
        model.addAttribute("pass", 50);
        model.addAttribute("fail", 100);
        return "administrador/grafico2";
    }

}

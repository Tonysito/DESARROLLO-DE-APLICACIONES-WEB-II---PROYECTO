package org.cibertec.controller;



import java.util.List;

import org.cibertec.entity.auditoria.ProductoBajoStock;

import org.cibertec.service.controlStockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/stock")
public class controlStockController {

    @Autowired
    private controlStockService service;

    // Endpoint para actualizar el reporte de productos bajos en stock
    @GetMapping("/actualizar")
    public List<ProductoBajoStock> actualizarBajoStock() {
        return service.actualizarReporteBajoStock();
    }

    // Endpoint para listar todos los productos bajos en stock
    @GetMapping("/listar")
    public List<ProductoBajoStock> listarBajoStock() {
        return service.listaBajoStock();
    }
}

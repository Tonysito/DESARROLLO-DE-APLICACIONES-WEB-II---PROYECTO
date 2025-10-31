package org.cibertec.service;

import java.util.List;

import org.cibertec.entity.Producto;
import org.cibertec.entity.auditoria.ProductoBajoStock;

public interface controlStockService {
	
	
	// LISTA DE REPORTE DE PRODUCTOS EN BAJO STOCK
	 public List<ProductoBajoStock> actualizarReporteBajoStock();
	 
	 
	 // LISTA DE TODOS LOS PRODUCTOS CON BAJO STOCK
	 public List<ProductoBajoStock> listaBajoStock();


	 
	 //OPERNFEIGN
	 public List<Producto> productosBajosClient(); 
}

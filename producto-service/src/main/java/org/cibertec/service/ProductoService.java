package org.cibertec.service;

import java.util.List;
import java.util.Optional;

import org.cibertec.entity.Producto;

public interface ProductoService {
	List<Producto> findAll();
	Optional<Producto> findById(Integer id);
	Producto save(Producto product);
	Producto update(Producto producto); 
	void deleteById(Integer id);
	
	 //agregado
	List<Producto> findBySubCategoria(Integer idSubCategoria);
	 //agregado
	List<Producto> findByLowStock(int stock);

	//PRODUCTOS ACTIVOS
	List<Producto> findAllActivos();
	
	
	//PRODUCTOS INACTIVOS
	List<Producto> findAllInactivos();

	
}

package org.cibertec.repository;

import java.util.List;

import org.cibertec.entity.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductoRepository  extends JpaRepository<Producto, Integer>{
	   List<Producto> findByNombreContainingIgnoreCase(String nombre);  
	    //agregado
	    List<Producto> findBySubCategoria_IdSubCategoria(Integer idSubCategoria);
	    
	    
	    //agregado
	    List<Producto> findByStockLessThan(int stock);
	    
	    

	    //  Método para traer productos activos
	    List<Producto> findByActivoTrue();

	    //  Método para traer productos inactivos
	    List<Producto> findByActivoFalse();
	    
}

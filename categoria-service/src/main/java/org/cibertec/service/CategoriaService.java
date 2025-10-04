package org.cibertec.service;

import java.util.List;
import java.util.Optional;

import org.cibertec.entity.Categoria;



public interface CategoriaService {
	
	 List<Categoria> getAllCategorias();
	 Optional<Categoria> getCategoriaById(Integer id);
	 Categoria createCategoria(Categoria categoria);
	 Categoria updateCategoria(Integer id, Categoria categoria); 
	 void deleteCategoria(Integer id);
  

}

package org.cibertec.controller;

import java.util.List;
import java.util.Optional;

import org.cibertec.entity.Categoria;
import org.cibertec.service.CategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/categorias")
public class CategoriaController {

	@Autowired
	private CategoriaService categoriaService;
	
	 @GetMapping
	    public List<Categoria> listar() {
		 return categoriaService.getAllCategorias();
	    }

	    @GetMapping("/{id}")
	    public Optional<Categoria> obtenerPorId(@PathVariable Integer id) {
	     return categoriaService.getCategoriaById(id);
	    }

	    @PostMapping
	    public Categoria crear(@RequestBody Categoria categoria) {
	    	 return categoriaService.createCategoria(categoria);
	    }

	    @DeleteMapping("/{id}")
	    public void eliminar(@PathVariable Integer id) {
	    	 categoriaService.deleteCategoria(id);
	    }
}

package org.cibertec.controller;

import java.util.List;

import org.cibertec.entity.SubCategoria;
import org.cibertec.service.SubCategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/subcategorias")
public class SubCategoriaController {

	 @Autowired
	    private SubCategoriaService subCategoriaService;

	    @GetMapping
	    public List<SubCategoria> listarSubCategorias() {
	        return subCategoriaService.listar();
	    }
	    
	    @GetMapping("/categoria/{idCategoria}")
	    public List<SubCategoria> obtenerPorCategoria(@PathVariable Integer idCategoria) {
	        return subCategoriaService.listarPorCategoria(idCategoria);
	    }

	    @GetMapping("/{id}")
	    public SubCategoria obtenerSubCategoria(@PathVariable Integer id) {
	        return subCategoriaService.buscarPorId(id);
	    }

	    @PostMapping
	    public SubCategoria guardarSubCategoria(@RequestBody SubCategoria subCategoria) {
	        return subCategoriaService.guardar(subCategoria);
	    }

	    @PutMapping("/{id}")
	    public SubCategoria actualizarSubCategoria(@PathVariable Integer id, @RequestBody SubCategoria subCategoria) {
	        SubCategoria existente = subCategoriaService.buscarPorId(id);
	        if (existente != null) {
	            existente.setNombre(subCategoria.getNombre());
	            existente.setCategoria(subCategoria.getCategoria());
	            return subCategoriaService.guardar(existente);
	        } else {
	            return null;
	        }
	    }

	    @DeleteMapping("/{id}")
	    public void eliminarSubCategoria(@PathVariable Integer id) {
	        SubCategoria sub = subCategoriaService.buscarPorId(id);
	        if (sub != null) {
	            subCategoriaService.eliminar(id);
	        }
	    }
}

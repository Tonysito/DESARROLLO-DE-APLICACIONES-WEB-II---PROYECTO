package org.cibertec.service;

import java.util.List;
import java.util.Optional;

import org.cibertec.entity.Categoria;
import org.cibertec.repository.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoriaServiceImpl implements CategoriaService{

	@Autowired
	private CategoriaRepository categoriaRepository;
	
	@Override
	public List<Categoria> getAllCategorias() {
		return categoriaRepository.findAll();
	}

	@Override
	public Optional<Categoria> getCategoriaById(Integer id) {
		   return categoriaRepository.findById(id);
	}

	@Override
	public Categoria createCategoria(Categoria categoria) {
		 return categoriaRepository.save(categoria);
	}

	@Override
	public Categoria updateCategoria(Integer id, Categoria categoria) {
		Optional<Categoria> opt = categoriaRepository.findById(id);
        if (opt.isPresent()) {
            Categoria cat = opt.get();
            cat.setNombre(categoria.getNombre());
            return categoriaRepository.save(cat);
        } else {
            throw new RuntimeException("Categoría no encontrada con ID: " + id);
        }
	}

	@Override
	public void deleteCategoria(Integer id) {
		categoriaRepository.deleteById(id);
	}

}

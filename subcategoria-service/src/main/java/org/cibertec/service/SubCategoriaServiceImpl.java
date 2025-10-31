package org.cibertec.service;

import java.util.List;

import org.cibertec.entity.SubCategoria;
import org.cibertec.repository.SubCategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SubCategoriaServiceImpl implements SubCategoriaService{

	 @Autowired
	    private SubCategoriaRepository subCategoriaRepository;

	    @Override
	    public List<SubCategoria> listar() {
	        return subCategoriaRepository.findAll();
	    }

	    @Override
	    public SubCategoria buscarPorId(Integer id) {
	        return subCategoriaRepository.findById(id).orElse(null);
	    }
	    @Override
	    public SubCategoria guardar(SubCategoria subCategoria) {
	        return subCategoriaRepository.save(subCategoria);
	    }
	    
	    @Override
	    public void eliminar(Integer id) {
	        subCategoriaRepository.deleteById(id);
	    }

		@Override
		public List<SubCategoria> listarPorCategoria(Integer idCategoria) {
			// TODO Auto-generated method stub
			return subCategoriaRepository.findByCategoria_IdCategoria(idCategoria);
		}


	
	

}

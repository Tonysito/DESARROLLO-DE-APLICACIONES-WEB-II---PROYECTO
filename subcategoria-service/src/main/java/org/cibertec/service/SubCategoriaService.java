package org.cibertec.service;

import java.util.List;

import org.cibertec.entity.SubCategoria;

public interface SubCategoriaService {

	List<SubCategoria> listar();
	List<SubCategoria> listarPorCategoria(Integer idCategoria);
	SubCategoria buscarPorId(Integer id);
	SubCategoria guardar(SubCategoria subCategoria);
	void eliminar(Integer id);

}

package org.cibertec.repository;

import java.util.List;

import org.cibertec.entity.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Integer>{
	 
	List<Categoria> findByNombreContainingIgnoreCase(String nombre);

}

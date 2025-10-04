package org.cibertec.repository;

import java.util.List;

import org.cibertec.entity.SubCategoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubCategoriaRepository extends JpaRepository<SubCategoria, Integer> {
	
	List<SubCategoria> findByCategoria_IdCategoria(Integer IdCategoria);
}

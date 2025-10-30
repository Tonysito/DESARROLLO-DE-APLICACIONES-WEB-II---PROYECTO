package org.cibertec.repository;

import java.util.Optional;

import org.cibertec.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends JpaRepository<Usuario, Integer>{

	
	Optional<Usuario> findByEmail(String email);
}

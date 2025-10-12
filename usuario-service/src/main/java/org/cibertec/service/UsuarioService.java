package org.cibertec.service;

import java.util.List;
import java.util.Optional;

import org.cibertec.entity.Usuario;
import org.cibertec.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

	  @Autowired
	    private UsuarioRepository usuariorepository;

	    public List<Usuario> listar() {
	        return usuariorepository.findAll();
	    }

	    public Optional<Usuario> buscarPorId(Integer id) {
	        return usuariorepository.findById(id);
	    }

	    public Usuario guardar(Usuario usuario) {
	        return usuariorepository.save(usuario);
	    }

	    public void eliminar(Integer id) {
	    	usuariorepository.deleteById(id);
	    }
	    
	    // método para login
	    public Optional<Usuario> validarCredenciales(String email, String password) {
	        Optional<Usuario> usuarioOpt = usuariorepository.findByEmail(email);
	        if (usuarioOpt.isPresent() && usuarioOpt.get().getPassword().equals(password)) {
	            return usuarioOpt;
	        }
	        return Optional.empty();
	    }
}

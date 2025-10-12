package org.cibertec.controller;

import java.util.List;
import java.util.Optional;

import org.cibertec.dto.LoginRequest;
import org.cibertec.entity.Usuario;
import org.cibertec.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

	  @Autowired
	    private  UsuarioService usuarioService;
	
	  @GetMapping
	    public List<Usuario> listarUsuarios() {
	        return usuarioService.listar();
	    }
	  
	  @GetMapping("/{id}")
	    public Optional<Usuario> ObtenerUsuario(@PathVariable Integer id) {
	        return usuarioService.buscarPorId(id);
	    }

	  @PostMapping
	  public Usuario crearUsuario(@RequestBody Usuario usuario) {
	        return usuarioService.guardar(usuario);
	    }

	   //@PutMapping("/{id}")
	    //public Usuario actualizarUsuario(@PathVariable Integer id, @RequestBody Usuario usuario) {
	      //  usuario.setIdUsuario(id);
	        //return usuarioService.guardar(usuario);
	    //}
	
	  @DeleteMapping("/{id}")
	    public void eliminarUsuario(@PathVariable Integer id) {
		  usuarioService.eliminar(id);
	    }
	  
	  
	  // endpoint para login
	    @PostMapping("/login")
	    public String login(@RequestBody LoginRequest loginRequest) {
	        Optional<Usuario> usuarioOpt = usuarioService.validarCredenciales(
	                loginRequest.getEmail(),
	                loginRequest.getPassword()
	        );

	        if (usuarioOpt.isPresent()) {
	            return "Login exitoso. Bienvenido " + usuarioOpt.get().getNombre();
	        } else {
	            return "Credenciales inválidas";
	        }
	    }
}

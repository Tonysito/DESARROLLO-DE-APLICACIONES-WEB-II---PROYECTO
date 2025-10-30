package org.cibertec.dto;

import java.util.List;

import org.cibertec.entity.Usuario;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

public class CustomUserDetails extends User{

	/**
	 * serialVersionUID es un identificador único para la serialización de objetos.
	 */
	private static final long serialVersionUID = 1L;
	

	private final Usuario usuario;
	
	public CustomUserDetails(Usuario usuario) {
		//primer parametro deberia ser el username, pero en este caso usamos el id del usuario
		super(usuario.getEmail(),
              usuario.getPassword(),
              List.of(new SimpleGrantedAuthority("ROLE_" + usuario.getRol().name())));
        this.usuario = usuario;
	}

	public Usuario getUsuario() {
        return usuario;
    }

	 public String getNombre() {
        return usuario.getNombre();
    }

    public String getEmail() {
        return usuario.getEmail();
    }

}
